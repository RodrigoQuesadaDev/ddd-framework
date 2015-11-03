package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent.EventType.ADD
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent.EventType.REMOVE
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver.Services
import com.aticosoft.appointments.mobile.business.domain.common.time.TimeService
import com.aticosoft.appointments.mobile.business.domain.model.common.*
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
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

/**
 * Created by Rodrigo Quesada on 15/10/15.
 */
//TODO this should be abstract?
@Suppress("NOTHING_TO_INLINE")
/*internal*/ abstract class EntityObserver<E : Entity>(
        private val s: Services<E>,
        private val entityType: Class<E>
) {
    companion object {
        val DATA_REFRESH_RATE_TIME = millis(500)
    }

    //TODO handle errors? Add Crashlytics for handling them (use RxJavaErrorHandler?)

    //TODO use retryWhen to add exponential back-off retry (starts at 0.5s to a max of 5s),
    //TODO for this probably a custom operator is necessary since RxJava retry*/CATCH*??? operators do not reset after a successful emission? (Use composition for this custom operator?)

    //TODO add consistent (?) parameter that indicates if the underlying db operation should be transactional

    private val totalCountFilters = arrayOf(EntityObservationFilter(entityType, ADD, REMOVE))
    protected open fun entityByIdFilters(id: Long): Array<EntityObservationFilter<*>> = arrayOf(EntityObservationFilter(entityType) { it.id == id })

    //TODO test/implement filter for get(id)
    fun observe(id: Long) = entityObservable(entityByIdFilters(id)) { s.entityRepository.get(id) }

    fun observe(query: UniqueQuery<E>) = entityObservable(query.filters) { s.entityRepository.find(query) }

    fun observe(query: ListQuery<E>) = entityObservable(query.filters) { s.entityRepository.find(query) }

    fun observe(query: CountQuery<E>) = entityObservable(query.filters) { s.entityRepository.count(query) }

    fun observeTotalCount() = entityObservable(totalCountFilters) { s.entityRepository.size() }

    //TODO queryView

    //TODO default filters based on query views

    //TODO test all observation methods?

    //TODO test filters
    //TODO filter must go BEFORE throttling
    //window->first of the window that satisfies filter OR null->flat map->not null

    //TODO use defaultOrEmpty? -->Nothing

    //TODO change to inline fun when KT-8668 is fixed
    private /*inline*/ fun <R> entityObservable(filters: Array<out EntityObservationFilter<*>>, queryExecution: () -> R): Observable<R> = merge(
            fromCallable(
                    { executeQuery(queryExecution) },
                    Schedulers.io()
            ),
            filters.group().toObservable()
                    .mergeWithEntityChangeEvents()
                    .throttleFirstChange(s.timeService.randomDuration(DATA_REFRESH_RATE_TIME), DATA_REFRESH_RATE_TIME)
                    .observeOn(Schedulers.io())
                    .map { executeQuery(queryExecution) }
    )

    private inline fun <R> executeQuery(queryExecution: () -> R): R = s.transactionManager.transactional { queryExecution() }

    private inline fun <T : FilterableEntityChangeEvent> Observable<T>.throttleFirstChange(initialIntervalDuration: Duration, intervalDuration: Duration): Observable<T> {
        val ticks = Observables.interval(initialIntervalDuration, intervalDuration).share()
        return window(ticks)
                .flatMap {
                    it.firstOrNull { e -> e.entityChange.matchesAll(e.filters) }
                            .filter { it != null }
                            .zipWith(ticks, { e, t -> e })
                }
    }

    private inline fun Array<out EntityObservationFilter<*>>.group(): List<FilterGroup> = groupBy { it.entityType }.map { FilterGroup(it.key, it.value.toTypedArray()) }

    private inline fun Observable<FilterGroup>.mergeWithEntityChangeEvents(): Observable<FilterableEntityChangeEvent> = flatMap(
            { filterGroup -> s.entityListenersManager.forType(filterGroup.type).changes },
            { filterGroup, e -> EntityObserver.FilterableEntityChangeEvent(e, filterGroup.filters) }
    )

    private inline fun EntityChangeEvent<*>.matchesAll(filters: Array<out EntityObservationFilter<*>>) = filters.all { filter -> filter(this) }

    private data class FilterGroup(val type: Class<*>, val filters: Array<EntityObservationFilter<*>>)

    private data class FilterableEntityChangeEvent(val entityChange: EntityChangeEvent<*>, val filters: Array<out EntityObservationFilter<*>>)

    abstract class Services<E : Entity> {
        lateinit var entityListenersManager: EntityListenersManager
            @Inject protected set
        lateinit var transactionManager: TransactionManager
            @Inject protected set
        lateinit var timeService: TimeService
            @Inject protected set
        abstract val entityRepository: Repository<E>
    }
}