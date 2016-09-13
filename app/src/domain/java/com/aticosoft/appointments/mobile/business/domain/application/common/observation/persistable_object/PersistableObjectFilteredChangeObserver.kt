@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object

import com.aticosoft.appointments.mobile.business.domain.common.time.TimeService
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.rodrigodev.common.rx.Observables
import com.rodrigodev.common.rx.firstOrNothing
import dagger.MembersInjector
import org.joda.time.Duration
import rx.Observable
import rx.lang.kotlin.toObservable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 08/09/16.
 */
/*internal*/ open class PersistableObjectFilteredChangeObserver<P : PersistableObject<*>> private constructor(
        private val dataRefreshRateTime: Duration? = null
) {
    private lateinit var m: InjectedMembers<P>

    fun observe(filters: Array<out PersistableObjectObservationFilter<*>>): Observable<FilterableObjectChangeEvent> {
        return filters.groupByType().toObservable()
                .mergeWithObjectChangeEvents()
                .throttle()
                .onBackpressureLatest()
    }

    //region Throttling
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
    //endregion

    //region Utils
    private inline fun Array<out PersistableObjectObservationFilter<*>>.groupByType(): List<FilterGroup> = groupBy { it.objectType }.map { FilterGroup(it.key, it.value.toTypedArray()) }

    private inline fun Observable<FilterGroup>.mergeWithObjectChangeEvents(): Observable<FilterableObjectChangeEvent> = mergeWith(Observable.never()).flatMap(
            { filterGroup -> m.objectListenersManager.forType(filterGroup.type).changes },
            { filterGroup, e -> FilterableObjectChangeEvent(e, filterGroup.filters) }
    )
    //endregion

    @Singleton
    class Factory<P : PersistableObject<*>> @Inject protected constructor(
            private val injector: MembersInjector<PersistableObjectFilteredChangeObserver<P>>
    ) {

        fun create(dataRefreshRateTime: Duration? = null) = PersistableObjectFilteredChangeObserver<P>(dataRefreshRateTime).apply {
            injector.injectMembers(this)
        }
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<P>) {
        m = injectedMembers
    }

    protected class InjectedMembers<P : PersistableObject<*>> @Inject constructor(
            val objectType: Class<P>,
            val objectListenersManager: PersistableObjectListenersManager,
            val timeService: TimeService
    )
    //endregion
}

//region Filter Matching
private inline fun FilterableObjectChangeEvent.matchesAllFilters() = objectChange.matchesAll(filters)

private inline fun PersistableObjectChangeEvent<*>.matchesAll(filters: Array<out PersistableObjectObservationFilter<*>>) = filters.all { filter -> filter(this) }
//endregion

//region Other Classes
private data class FilterGroup(val type: Class<out PersistableObject<*>>, val filters: Array<PersistableObjectObservationFilter<*>>)

/*internal*/ data class FilterableObjectChangeEvent(val objectChange: PersistableObjectChangeEvent<*>, val filters: Array<out PersistableObjectObservationFilter<*>>)
//endregion