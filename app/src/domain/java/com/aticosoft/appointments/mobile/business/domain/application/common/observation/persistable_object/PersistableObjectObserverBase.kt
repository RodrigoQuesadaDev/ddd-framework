@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryViewsManager
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent.EventType.ADD
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent.EventType.REMOVE
import com.aticosoft.appointments.mobile.business.domain.common.time.TimeService
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.*
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.collection.plus
import com.rodrigodev.common.rx.Observables
import com.rodrigodev.common.rx.firstOrNothing
import org.joda.time.Duration
import rx.Observable
import rx.Observable.never
import rx.lang.kotlin.toObservable
import rx.schedulers.Schedulers
import rx.util.async.Async.fromCallable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/10/15.
 */
@Singleton
/*internal*/ open class PersistableObjectObserverBase<P : PersistableObject<I>, I, R : Repository<P, I>> protected constructor(
        private val dataRefreshRateTime: Duration? = null
) {
    @Inject protected constructor() : this(null)

    private lateinit var m: InjectedMembers<P, R>

    protected open val defaultQueryView = QueryView.DEFAULT

    protected open fun objectByIdFilters(id: I): Array<PersistableObjectObservationFilter<*>> = arrayOf(PersistableObjectObservationFilter(m.objectType) { it.id == id })

    private val totalCountFilters by lazy { arrayOf(PersistableObjectObservationFilter(m.objectType, ADD, REMOVE)) }

    fun observe(id: I, queryView: QueryView = defaultQueryView) = objectObservable(queryView, objectByIdFilters(id)) { m.repository.get(id) }

    fun observe(query: UniqueQuery<P>, queryView: QueryView = defaultQueryView) = objectObservable(queryView, query) { m.repository.find(query) }

    fun observe(query: ListQuery<P>, queryView: QueryView = defaultQueryView) = objectObservable(queryView, query) { m.repository.find(query) }

    fun observe(query: CountQuery<P>) = objectObservable(QueryView.DEFAULT, query) { m.repository.count(query) }

    fun observeTotalCount() = objectObservable(QueryView.DEFAULT, totalCountFilters) { m.repository.size() }

    protected open fun Array<out PersistableObjectObservationFilter<*>>.plusDefaultFiltersFrom(queryView: QueryView) = this + queryView.defaultFiltersFor(this)

    private inline fun <R> objectObservable(queryView: QueryView, query: Query<*>, crossinline queryExecution: () -> R): Observable<R> = objectObservable(queryView, query.filters, queryExecution)

    private inline fun <R> objectObservable(queryView: QueryView, filters: Array<out PersistableObjectObservationFilter<*>>, crossinline queryExecution: () -> R): Observable<R> {
        val queryResult = fromCallable(
                { executeQuery(queryView, queryExecution) },
                Schedulers.io()
        )

        val changes = filters.plusDefaultFiltersFrom(queryView)
                .groupByType().toObservable()
                .mergeWithObjectChangeEvents()
                .throttle()
                .onBackpressureLatest()

        return queryResult.repeatWhen({ o -> o.zipWith(changes, { v, c -> c }) })
    }

    private inline fun <R> executeQuery(queryView: QueryView, queryExecution: () -> R): R = m.persistenceContext.execute(false) { m.queryViewsManager.withView(queryView, queryExecution) }

    private inline fun Observable<FilterableObjectChangeEvent>.throttle(): Observable<FilterableObjectChangeEvent> {
        return if (dataRefreshRateTime != null) throttleFirstChange(m.timeService.randomDuration(dataRefreshRateTime), dataRefreshRateTime)
        else filter(FilterableObjectChangeEvent::matchesAllFilters)
    }

    private inline fun Observable<FilterableObjectChangeEvent>.throttleFirstChange(initialIntervalDuration: Duration, intervalDuration: Duration): Observable<FilterableObjectChangeEvent> {
        val ticks = Observables.interval(initialIntervalDuration, intervalDuration).share()
        return window(ticks)
                .flatMap {
                    it.firstOrNothing(FilterableObjectChangeEvent::matchesAllFilters)
                            .zipWith(ticks, { e, t -> e })
                }
    }

    private inline fun Array<out PersistableObjectObservationFilter<*>>.groupByType(): List<FilterGroup> = groupBy { it.objectType }.map { FilterGroup(it.key, it.value.toTypedArray()) }

    private inline fun Observable<FilterGroup>.mergeWithObjectChangeEvents(): Observable<FilterableObjectChangeEvent> = mergeWith(never()).flatMap(
            { filterGroup -> m.objectListenersManager.forType(filterGroup.type).changes },
            { filterGroup, e -> FilterableObjectChangeEvent(e, filterGroup.filters) }
    )

    private data class FilterGroup(val type: Class<out PersistableObject<*>>, val filters: Array<PersistableObjectObservationFilter<*>>)

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<P, R>) {
        m = injectedMembers
    }

    protected class InjectedMembers<P : PersistableObject<*>, R : Repository<P, *>> @Inject constructor(
            val objectType: Class<P>,
            val repository: R,
            val objectListenersManager: PersistableObjectListenersManager,
            val queryViewsManager: QueryViewsManager,
            val persistenceContext: PersistenceContext,
            val timeService: TimeService
    )
    //endregion
}

private inline fun FilterableObjectChangeEvent.matchesAllFilters() = objectChange.matchesAll(filters)

private inline fun PersistableObjectChangeEvent<*>.matchesAll(filters: Array<out PersistableObjectObservationFilter<*>>) = filters.all { filter -> filter(this) }

private data class FilterableObjectChangeEvent(val objectChange: PersistableObjectChangeEvent<*>, val filters: Array<out PersistableObjectObservationFilter<*>>)