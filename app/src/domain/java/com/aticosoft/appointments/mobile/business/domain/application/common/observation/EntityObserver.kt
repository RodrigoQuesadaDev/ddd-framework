package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver.Services
import com.aticosoft.appointments.mobile.business.domain.infrastructure.rx.RxSchedulers
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import rx.Observable
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * Created by rodrigo on 15/10/15.
 */
//TODO this should be abstract?
open internal class EntityObserver<E : Entity>(
        private val s: Services,
        private val entityRepository: Repository<E>,
        private val entityType: KClass<E>
) {
    //TODO handle errors? Add Crashlytics for handling them

    //TODO use retryWhen to add exponential back-off retry (starts at 0.5s to a max of 5s),
    //TODO for this probably a custom operator is necessary since RxJava retry* operators do not reset after a successful emission? (Use composition for this custom operator?)

    //TODO add consistent (?) parameter that indicates if the underlying db operation should be transactional

    val callbackListener = EntityCallbackListener<E>(s.listenerServices, entityType)
    val entityChanges = callbackListener.observeChanges()

    fun observe(id: Long) = entityObservable { entityRepository.get(id) }

    fun observe(query: UniqueQuery<E>) = entityObservable { entityRepository.find(query) }

    fun observe(query: ListQuery<E>) = entityObservable { entityRepository.find(query) }

    fun observeSize() = entityObservable { entityRepository.size() }

    //TODO change to inline fun when KT-8668 is fixed
    private /*inline*/ fun <R> entityObservable(crossinline queryExecution: () -> R): Observable<R> {
        return Observable.create<R> {
            it.onNext(s.transactionManager.transactional { queryExecution() })
        }.subscribeOn(s.rxSchedulers.io())
    }

    class Services @Inject constructor(
            val listenerServices: EntityCallbackListener.Services,
            val transactionManager: TransactionManager,
            val rxSchedulers: RxSchedulers
    )
}