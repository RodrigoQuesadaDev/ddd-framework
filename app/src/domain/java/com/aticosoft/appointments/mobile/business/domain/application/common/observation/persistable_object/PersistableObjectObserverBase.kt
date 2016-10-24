@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryViewsManager
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent.EventType.ADD
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent.EventType.REMOVE
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.*
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.collection.plus
import com.rodrigodev.common.properties.Delegates.postInitialized
import com.rodrigodev.common.properties.UnsafePostInitialized
import com.rodrigodev.common.properties.delegates.UnsafePostInitializedPropertyDelegate.UnsafePropertyInitializer
import com.rodrigodev.common.rx.repeatWhenChangeOccurs
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
) : UnsafePostInitialized {

    private lateinit var m: InjectedMembers<P, R>
    override val _propertyInitializer = UnsafePropertyInitializer()

    private val changeObserver by postInitialized { m.changeObserverFactory.create(dataRefreshRateTime) }

    protected open val defaultQueryView = QueryView.DEFAULT

    protected open fun objectByIdFilters(id: I): Array<PersistableObjectObservationFilter<*>> = arrayOf(PersistableObjectObservationFilter(m.objectType) { it.id == id })

    private val totalCountFilters by postInitialized { arrayOf(PersistableObjectObservationFilter(m.objectType, ADD, REMOVE)) }

    fun observe(id: I, queryView: QueryView = defaultQueryView) = objectObservable(queryView, objectByIdFilters(id)) { m.repository.get(id) }

    fun observe(query: UniqueQuery<P>, queryView: QueryView = defaultQueryView) = objectObservable(queryView, query) { m.repository.find(query) }

    fun observe(query: ListQuery<P>, queryView: QueryView = defaultQueryView) = objectObservable(queryView, query) { m.repository.find(query) }

    fun observe(query: CountQuery<P>) = objectObservable(QueryView.DEFAULT, query) { m.repository.count(query) }

    fun observeTotalCount() = objectObservable(QueryView.DEFAULT, totalCountFilters) { m.repository.size() }

    private inline fun <T> objectObservable(queryView: QueryView, query: Query<*>, crossinline queryExecution: () -> T): Observable<T> = objectObservable(queryView, query.filters, queryExecution)

    private inline fun <T> objectObservable(queryView: QueryView, filters: Array<out PersistableObjectObservationFilter<*>>, crossinline queryExecution: () -> T): Observable<T> {
        return fromCallable(
                { executeQuery(queryView, queryExecution) },
                Schedulers.io()
        )
                .repeatWhenChangeOccurs(queryView, filters)
    }

    // observation is performed outside transactions, that way they should perform better
    private inline fun <T> executeQuery(queryView: QueryView, queryExecution: () -> T): T = m.persistenceContext.execute(false) { m.queryViewsManager.withView(queryView, queryExecution) }

    //region Utils
    private inline fun <T> Observable<T>.repeatWhenChangeOccurs(queryView: QueryView, filters: Array<out PersistableObjectObservationFilter<*>>): Observable<T> {
        val changes = changeObserver.observe(filters.plusDefaultFiltersFrom(queryView))
        return repeatWhenChangeOccurs(changes)
    }

    protected open fun Array<out PersistableObjectObservationFilter<*>>.plusDefaultFiltersFrom(queryView: QueryView) = this + queryView.defaultFiltersFor(this)
    //endregion

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<P, R>) {
        m = injectedMembers
        _init()
    }

    protected class InjectedMembers<P : PersistableObject<*>, R : Repository<P, *>> @Inject constructor(
            val objectType: Class<P>,
            val repository: R,
            val queryViewsManager: QueryViewsManager,
            val persistenceContext: PersistenceContext,
            val changeObserverFactory: PersistableObjectFilteredChangeObserver.Factory<P>
    )
    //endregion
}