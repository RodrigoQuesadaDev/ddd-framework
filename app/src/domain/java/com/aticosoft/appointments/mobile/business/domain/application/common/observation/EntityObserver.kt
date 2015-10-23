package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver.Services
import com.aticosoft.appointments.mobile.business.domain.common.time.TimeService
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import com.rodrigodev.common.rx.throttleLast
import org.joda.time.Duration.millis
import rx.Observable
import rx.Observable.merge
import rx.schedulers.Schedulers
import rx.util.async.Async.fromCallable
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by rodrigo on 15/10/15.
 */
//TODO this should be abstract?
open /*internal*/ class EntityObserver<E : Entity>(
        private val s: Services,
        private val entityRepository: Repository<E>,
        private val entityType: KClass<E>
) {
    private companion object {
        val DATA_REFRESH_RATE_TIME = millis(500)
    }
    //TODO handle errors? Add Crashlytics for handling them (use RxJavaErrorHandler?)

    //TODO use retryWhen to add exponential back-off retry (starts at 0.5s to a max of 5s),
    //TODO for this probably a custom operator is necessary since RxJava retry*/CATCH*??? operators do not reset after a successful emission? (Use composition for this custom operator?)

    //TODO add consistent (?) parameter that indicates if the underlying db operation should be transactional

    //TODO should this field be exposed after changes to registration of listeners?
    protected open val entityListener = EntityCallbackListener(s.listenerServices, entityType)
    private val entityChanges = entityListener.changes

    fun register() {
        entityListener.register()
    }

    fun observe(id: Long) = entityObservable { entityRepository.get(id) }

    fun observe(query: UniqueQuery<E>) = entityObservable { entityRepository.find(query) }

    fun observe(query: ListQuery<E>) = entityObservable { entityRepository.find(query) }

    fun observeSize() = entityObservable { entityRepository.size() }

    //TODO filter must go BEFORE throttling

    //TODO observe type of change? (Such as DELETE or UPDATE. Check InstanceLifecycleEvent class.)

    //TODO specs for observed changes on entityChanges

    //TODO use defaultOrEmpty? -->Nothing

    //TODO change to inline fun when KT-8668 is fixed
    private /*inline*/ fun <R> entityObservable(queryExecution: () -> R): Observable<R> {
        return merge(
                fromCallable(
                        { executeQuery(queryExecution) },
                        Schedulers.io()
                ),
                entityChanges
                        .throttleLast(s.timeService.randomDuration(DATA_REFRESH_RATE_TIME), DATA_REFRESH_RATE_TIME)
                        .observeOn(Schedulers.io())
                        .map { executeQuery(queryExecution) }
        )
    }

    private inline fun <R> executeQuery(queryExecution: () -> R): R = s.transactionManager.transactional { queryExecution() }

    class Services @Inject constructor(
            val listenerServices: EntityCallbackListener.Services,
            val transactionManager: TransactionManager,
            val timeService: TimeService
    )
}