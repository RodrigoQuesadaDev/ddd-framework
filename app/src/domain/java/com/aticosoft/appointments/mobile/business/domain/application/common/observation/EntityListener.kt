package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent.EventType
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.EntityLifecycleListener
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
import com.rodrigodev.common.properties.delegates.ThreadLocalDelegate
import rx.Observable
import rx.lang.kotlin.PublishSubject
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.jdo.JDOHelper
import javax.jdo.listener.*

/**
 * Created by Rodrigo Quesada on 18/10/15.
 */
/*internal*/ class EntityListener<E : Entity>(
        private val s: Services,
        override val entityType: Class<E>
) : EntityLifecycleListener<E>, Entity.EntityStateAccess, CreateLifecycleListener, StoreLifecycleListener, DeleteLifecycleListener, DirtyLifecycleListener {

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

    fun resetLocalState() = threadLocalCleaner.cleanUpThreadLocalInstances()

    fun onTransactionCommitted() {
        if (newEntityChanges) {
            // Prevent reentrant execution caused by recursion (onNext triggers a new transaction)
            newEntityChanges = false
            entityChanges.forEach { publisher.onNext(it) }
            // No need to remove objects from list as resetLocalState should be called afterwards
        }
    }

    override fun preDirty(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        (event.source as E).let { entity ->
            entity.previousValue = s.persistenceContext.persistenceManager.detachCopy(entity)
        }
    }

    override fun postCreate(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        entityChanges.add(EntityChangeEvent(EventType.from(event.eventType), currentValue = event.source as E))
    }

    override fun postStore(event: InstanceLifecycleEvent) {
        //only updates
        if (!JDOHelper.isNew(event.source)) {
            @Suppress("UNCHECKED_CAST")
            (event.source as E).let { entity ->
                entityChanges.add(EntityChangeEvent(EventType.from(event.eventType), previousValue = entity.previousValue as E, currentValue = entity))
            }
        }
    }

    override fun postDelete(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        entityChanges.add(EntityChangeEvent(EventType.from(event.eventType), previousValue = event.source as E))
    }

    override fun preStore(event: InstanceLifecycleEvent) {
        // Do nothing!
    }

    override fun preDelete(event: InstanceLifecycleEvent) {
        // Do nothing!
    }

    override fun postDirty(event: InstanceLifecycleEvent?) {
        // Do nothing!
    }

    class Services @Inject protected constructor(
            val persistenceContext: PersistenceContext
    )
}