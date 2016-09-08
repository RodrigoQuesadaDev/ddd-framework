@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryViewsManager
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent.EventType.ADD
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent.EventType.REMOVE
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.*
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.collection.plus
import org.joda.time.Duration
import rx.Observable
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
    private lateinit var m: InjectedMembers<P, I, R>

    private val changeObserver by lazy { m.changeObserverFactory.create(dataRefreshRateTime) }

    protected open val defaultQueryView = QueryView.DEFAULT

    protected open fun objectByIdFilters(id: I): Array<PersistableObjectObservationFilter<*>> = arrayOf(PersistableObjectObservationFilter(m.objectType) { it.id == id })

    private val totalCountFilters by lazy { arrayOf(PersistableObjectObservationFilter(m.objectType, ADD, REMOVE)) }

    fun observe(id: I, queryView: QueryView = defaultQueryView) = objectObservable(queryView, objectByIdFilters(id)) { m.repository.get(id) }

    fun observe(query: UniqueQuery<P>, queryView: QueryView = defaultQueryView) = objectObservable(queryView, query) { m.repository.find(query) }

    fun observe(query: ListQuery<P>, queryView: QueryView = defaultQueryView) = objectObservable(queryView, query) { m.repository.find(query) }

    fun observe(query: CountQuery<P>) = objectObservable(QueryView.DEFAULT, query) { m.repository.count(query) }

    fun observeTotalCount() = objectObservable(QueryView.DEFAULT, totalCountFilters) { m.repository.size() }

    private inline fun <R> objectObservable(queryView: QueryView, query: Query<*>, crossinline queryExecution: () -> R): Observable<R> = objectObservable(queryView, query.filters, queryExecution)

    private inline fun <R> objectObservable(queryView: QueryView, filters: Array<out PersistableObjectObservationFilter<*>>, crossinline queryExecution: () -> R): Observable<R> {
        return fromCallable(
                { executeQuery(queryView, queryExecution) },
                Schedulers.io()
        )
                .repeatWhenChangeOccurs(queryView, filters)
    }

    private inline fun <R> executeQuery(queryView: QueryView, queryExecution: () -> R): R = m.persistenceContext.execute(false) { m.queryViewsManager.withView(queryView, queryExecution) }

    //region Utils
    private inline fun <R> Observable<R>.repeatWhenChangeOccurs(queryView: QueryView, filters: Array<out PersistableObjectObservationFilter<*>>): Observable<R> {
        val changes = changeObserver.observe(filters.plusDefaultFiltersFrom(queryView))
        return repeatWhen({ o -> o.zipWith(changes, { v, c -> c }) })
    }

    protected open fun Array<out PersistableObjectObservationFilter<*>>.plusDefaultFiltersFrom(queryView: QueryView) = this + queryView.defaultFiltersFor(this)
    //endregion

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<P, I, R>) {
        m = injectedMembers
    }

    protected class InjectedMembers<P : PersistableObject<I>, I, R : Repository<P, I>> @Inject constructor(
            val objectType: Class<P>,
            val repository: R,
            val queryViewsManager: QueryViewsManager,
            val persistenceContext: PersistenceContext,
            val changeObserverFactory: PersistableObjectFilteredChangeObserver.Factory<P, I, R>
    )
    //endregion
}