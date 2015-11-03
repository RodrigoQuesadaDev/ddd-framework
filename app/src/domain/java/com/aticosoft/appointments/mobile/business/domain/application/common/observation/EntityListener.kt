package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent.EventType
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener.Services
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
import com.rodrigodev.common.properties.delegates.ThreadLocalDelegate
import rx.Observable
import rx.lang.kotlin.PublishSubject
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.jdo.JDOHelper
import javax.jdo.listener.CreateLifecycleListener
import javax.jdo.listener.DeleteLifecycleListener
import javax.jdo.listener.InstanceLifecycleEvent
import javax.jdo.listener.StoreLifecycleListener

/**
 * Created by Rodrigo Quesada on 18/10/15.
 */
/*internal*/ abstract class EntityListener<E : Entity>(
        private val s: Services,
        val entityType: Class<E>
) : CreateLifecycleListener, StoreLifecycleListener, DeleteLifecycleListener {

    //TODO create JdoEntityListenerBase on infrastructure stuff??? (this class pertains to domain)
    //TODO create JdoEntityChangeEvent on infrastructure stuff??? (this class pertains to domain)

    private val threadLocalCleaner = ThreadLocalCleaner()
    private val publisher = PublishSubject<EntityChangeEvent<E>>()
    val changes: Observable<EntityChangeEvent<E>> = publisher.observeOn(Schedulers.computation())
    // Meant to avoid unnecessary empty ArrayList allocation and subsequent iteration
    private var newEntityChanges: Boolean by ThreadLocalDelegate(threadLocalCleaner) { false }
    private val entityChanges: MutableList<EntityChangeEvent<E>> by ThreadLocalDelegate(threadLocalCleaner) {
        newEntityChanges = true
        arrayListOf<EntityChangeEvent<E>>()
    }

    fun register() {
        s.persistenceContext.registerEntityListener(this)
    }

    fun onTransactionCommitted() {
        if (newEntityChanges) {
            // Prevent reentrant execution caused by recursion (onNext triggers a new transaction)
            newEntityChanges = false
            entityChanges.forEach { publisher.onNext(it) }
            // No need to remove objects from list as resetLocalState should be called afterwards
        }
    }

    fun resetLocalState() = threadLocalCleaner.cleanUpThreadLocalInstances()

    private fun onModifiedEvent(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        val entity = (event.persistentInstance as E)
        entityChanges.add(EntityChangeEvent(EventType.from(event.eventType), entity))
    }

    override fun postCreate(event: InstanceLifecycleEvent) = onModifiedEvent(event)

    override fun preStore(event: InstanceLifecycleEvent) {
        // Do nothing!
    }

    override fun postStore(event: InstanceLifecycleEvent) {
        if (!JDOHelper.isNew(event.persistentInstance)) {
            onModifiedEvent(event)
        }
    }

    override fun preDelete(event: InstanceLifecycleEvent) {
        // Do nothing!
    }

    override fun postDelete(event: InstanceLifecycleEvent) = onModifiedEvent(event)

    class Services @Inject constructor(
            val persistenceContext: PersistenceContext
    )
}