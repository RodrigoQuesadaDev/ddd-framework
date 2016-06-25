@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent.EventType.ADD
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent.EventType.REMOVE
import com.aticosoft.appointments.mobile.business.domain.common.time.TimeService
import com.aticosoft.appointments.mobile.business.domain.model.common.*
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.collection.plus
import com.rodrigodev.common.rx.Observables
import com.rodrigodev.common.rx.firstOrNull
import org.joda.time.Duration
import org.joda.time.Duration.millis
import rx.Observable
import rx.Observable.merge
import rx.lang.kotlin.toObservable
import rx.schedulers.Schedulers
import rx.util.async.Async.fromCallable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/10/15.
 */
@Singleton
/*internal*/ open class EntityObserver<E : Entity> @Inject protected constructor() {
    companion object {
        val DATA_REFRESH_RATE_TIME = millis(500)
    }

    private lateinit var m: InjectedMembers<E>

    protected open val defaultQueryView = QueryView.DEFAULT

    private val totalCountFilters by lazy { arrayOf(EntityObservationFilter(m.entityType, ADD, REMOVE)) }

    protected open fun entityByIdFilters(id: String): Array<EntityObservationFilter<*>> = arrayOf(EntityObservationFilter(m.entityType) { it.id == id })

    fun observe(id: String, queryView: QueryView = defaultQueryView) = entityObservable(queryView, entityByIdFilters(id)) { m.entityRepository.get(id) }

    fun observe(query: UniqueQuery<E>, queryView: QueryView = defaultQueryView) = entityObservable(queryView, query) { m.entityRepository.find(query) }

    fun observe(query: ListQuery<E>, queryView: QueryView = defaultQueryView) = entityObservable(queryView, query) { m.entityRepository.find(query) }

    fun observe(query: CountQuery<E>) = entityObservable(QueryView.DEFAULT, query) { m.entityRepository.count(query) }

    fun observeTotalCount() = entityObservable(QueryView.DEFAULT, totalCountFilters) { m.entityRepository.size() }

    private inline fun <R> entityObservable(queryView: QueryView, query: Query<*>, crossinline queryExecution: () -> R): Observable<R> = entityObservable(queryView, query.filters, queryExecution)

    private inline fun <R> entityObservable(queryView: QueryView, filters: Array<out EntityObservationFilter<*>>, crossinline queryExecution: () -> R): Observable<R> = merge(
            fromCallable(
                    { executeQuery(queryView, queryExecution) },
                    Schedulers.io()
            ),
            filters.plusDefaultFiltersFrom(queryView)
                    .groupByType().toObservable()
                    .mergeWithEntityChangeEvents()
                    .throttleFirstChange(m.timeService.randomDuration(DATA_REFRESH_RATE_TIME), DATA_REFRESH_RATE_TIME)
                    .observeOn(Schedulers.io())
                    .map { executeQuery(queryView, queryExecution) }
    )

    protected open fun Array<out EntityObservationFilter<*>>.plusDefaultFiltersFrom(queryView: QueryView) = this + queryView.defaultFiltersFor(this)

    private inline fun <R> executeQuery(queryView: QueryView, queryExecution: () -> R): R = m.persistenceContext.execute(false) { m.queryViewsManager.withView(queryView, queryExecution) }

    private inline fun Observable<FilterableEntityChangeEvent>.throttleFirstChange(initialIntervalDuration: Duration, intervalDuration: Duration): Observable<FilterableEntityChangeEvent> {
        val ticks = Observables.interval(initialIntervalDuration, intervalDuration).share()
        return window(ticks)
                .flatMap {
                    it.firstOrNull { e -> e.entityChange.matchesAll(e.filters) }
                            .filter { it != null }
                            .zipWith(ticks, { e, t -> e })
                }
    }

    private inline fun Array<out EntityObservationFilter<*>>.groupByType(): List<FilterGroup> = groupBy { it.entityType }.map { FilterGroup(it.key, it.value.toTypedArray()) }

    private inline fun Observable<FilterGroup>.mergeWithEntityChangeEvents(): Observable<FilterableEntityChangeEvent> = flatMap(
            { filterGroup -> m.entityListenersManager.forType(filterGroup.type).changes },
            { filterGroup, e -> EntityObserver.FilterableEntityChangeEvent(e, filterGroup.filters) }
    )

    private inline fun EntityChangeEvent<*>.matchesAll(filters: Array<out EntityObservationFilter<*>>) = filters.all { filter -> filter(this) }

    private data class FilterGroup(val type: Class<out Entity>, val filters: Array<EntityObservationFilter<*>>)

    private data class FilterableEntityChangeEvent(val entityChange: EntityChangeEvent<*>, val filters: Array<out EntityObservationFilter<*>>)

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<E>) {
        m = injectedMembers
    }

    protected class InjectedMembers<E : Entity> @Inject constructor(
            val entityType: Class<E>,
            val entityRepository: Repository<E>,
            val entityListenersManager: EntityListenersManager,
            val queryViewsManager: QueryViewsManager,
            val persistenceContext: PersistenceContext,
            val timeService: TimeService
    )
    //endregion
}
