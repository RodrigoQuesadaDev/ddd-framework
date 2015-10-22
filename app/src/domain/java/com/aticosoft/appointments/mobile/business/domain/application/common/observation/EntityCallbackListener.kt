package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityCallbackListener.Services
import com.aticosoft.appointments.mobile.business.domain.infrastructure.rx.RxSchedulers
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import rx.Observable
import rx.lang.kotlin.PublishSubject
import java.util.*
import javax.inject.Inject
import javax.jdo.PersistenceManagerFactory
import javax.jdo.listener.DeleteLifecycleListener
import javax.jdo.listener.InstanceLifecycleEvent
import javax.jdo.listener.StoreLifecycleListener
import kotlin.reflect.KClass

/**
 * Created by rodrigo on 18/10/15.
 */
internal class EntityCallbackListener<E : Entity>(
        private val s: Services,
        private val entityType: KClass<E>
) : StoreLifecycleListener, DeleteLifecycleListener {

    private val publisher = PublishSubject<E>()

    private val modifiedEntitiesTL = object : ThreadLocal<MutableList<E>>() {
        override fun initialValue() = ArrayList<E>()
    }

    private val modifiedEntities: MutableList<E>
        get() = modifiedEntitiesTL.get()

    fun register(pmf: PersistenceManagerFactory) = pmf.addInstanceLifecycleListener(this, arrayOf(entityType.java))

    fun observeChanges(): Observable<E> = publisher.observeOn(s.rxSchedulers.computation())

    fun onTransactionCommitted() = modifiedEntities.forEach { publisher.onNext(it) }
    // No need to remove objects from list as resetLocalState should be called afterwards

    fun resetLocalState() = modifiedEntitiesTL.remove()

    private fun onModifiedEvent(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        val entity = event.persistentInstance as E
        modifiedEntities.add(entity)
    }

    override fun preStore(event: InstanceLifecycleEvent) {
        // Do nothing!
    }

    override fun postStore(event: InstanceLifecycleEvent) = onModifiedEvent(event)

    override fun preDelete(event: InstanceLifecycleEvent) {
        // Do nothing!
    }

    override fun postDelete(event: InstanceLifecycleEvent) = onModifiedEvent(event)

    class Services @Inject constructor(
            val rxSchedulers: RxSchedulers
    )
}