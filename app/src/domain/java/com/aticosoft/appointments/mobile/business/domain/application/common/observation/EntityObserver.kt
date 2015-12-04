package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent.EventType.ADD
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent.EventType.REMOVE
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver.Services
import com.aticosoft.appointments.mobile.business.domain.common.time.TimeService
import com.aticosoft.appointments.mobile.business.domain.model.common.*
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
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

/**
 * Created by Rodrigo Quesada on 15/10/15.
 */
//TODO this should be abstract?
@Suppress("NOTHING_TO_INLINE")
/*internal*/ abstract class EntityObserver<E : Entity>(
        private val s: Services,
        private val entityRepository: Repository<E>,
        private val entityType: Class<E>
) {
    companion object {
        val DATA_REFRESH_RATE_TIME = millis(500)
    }

    protected open val defaultQueryView = QueryView.DEFAULT

    //TODO handle errors? Add Crashlytics for handling them (use RxJavaErrorHandler?)

    //TODO use retryWhen to add exponential back-off retry (starts at 0.5s to a max of 5s),
    //TODO for this probably a custom operator is necessary since RxJava retry*/CATCH*??? operators do not reset after a successful emission? (Use composition for this custom operator?)

    //TODO add consistent (?) parameter that indicates if the underlying db operation should be transactional (thinking about it this is probably not necessary + observation should be non-transactional)

    private val totalCountFilters = arrayOf(EntityObservationFilter(entityType, ADD, REMOVE))

    protected open fun entityByIdFilters(id: Long): Array<EntityObservationFilter<*>> = arrayOf(EntityObservationFilter(entityType) { it.id == id })

    //TODO test/implement filter for get(id)
    fun observe(id: Long, queryView: QueryView = defaultQueryView) = entityObservable(queryView, entityByIdFilters(id)) { entityRepository.get(id) }

    fun observe(query: UniqueQuery<E>, queryView: QueryView = defaultQueryView) = entityObservable(queryView, query) { entityRepository.find(query) }

    fun observe(query: ListQuery<E>, queryView: QueryView = defaultQueryView) = entityObservable(queryView, query) { entityRepository.find(query) }

    fun observe(query: CountQuery<E>) = entityObservable(QueryView.DEFAULT, query) { entityRepository.count(query) }

    fun observeTotalCount() = entityObservable(QueryView.DEFAULT, totalCountFilters) { entityRepository.size() }

    //TODO queryView

    //TODO think carefully about detach process, where should it occur? (after service call? after query for observation?)

    //TODO default filters based on query views

    //TODO test all observation methods?

    //TODO use defaultOrEmpty? -->Nothing

    //TODO inject entityContext at load time

    //TODO take into account rollbacks during transactions???

    //TODO implement test-time command implementation (entities use EntityDelegate)

    private inline fun <R> entityObservable(queryView: QueryView, query: Query<*>, crossinline queryExecution: () -> R): Observable<R> = entityObservable(queryView, query.filters, queryExecution)

    private inline fun <R> entityObservable(queryView: QueryView, filters: Array<out EntityObservationFilter<*>>, crossinline queryExecution: () -> R): Observable<R> = merge(
            fromCallable(
                    { executeQuery(queryView, queryExecution) },
                    Schedulers.io()
            ),
            filters.plusDefaultFiltersFrom(queryView)
                    .groupByType().toObservable()
                    .mergeWithEntityChangeEvents()
                    .throttleFirstChange(s.timeService.randomDuration(DATA_REFRESH_RATE_TIME), DATA_REFRESH_RATE_TIME)
                    .observeOn(Schedulers.io())
                    .map { executeQuery(queryView, queryExecution) }
    )

    protected open fun Array<out EntityObservationFilter<*>>.plusDefaultFiltersFrom(queryView: QueryView) = this + queryView.defaultFiltersFor(this)

    private inline fun <R> executeQuery(queryView: QueryView, queryExecution: () -> R): R = s.tm.transactional {
        s.queryViewsManager.withView(queryView, queryExecution)
    }

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
            { filterGroup -> s.entityListenersManager.forType(filterGroup.type).changes },
            { filterGroup, e -> EntityObserver.FilterableEntityChangeEvent(e, filterGroup.filters) }
    )

    private inline fun EntityChangeEvent<*>.matchesAll(filters: Array<out EntityObservationFilter<*>>) = filters.all { filter -> filter(this) }

    private data class FilterGroup(val type: Class<out Entity>, val filters: Array<EntityObservationFilter<*>>)

    private data class FilterableEntityChangeEvent(val entityChange: EntityChangeEvent<*>, val filters: Array<out EntityObservationFilter<*>>)

    class Services @Inject constructor(
            val entityListenersManager: EntityListenersManager,
            val tm: TransactionManager,
            val queryViewsManager: QueryViewsManager,
            val timeService: TimeService
    )
}
