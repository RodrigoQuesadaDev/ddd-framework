package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityCallbackListener.Services
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
import com.rodrigodev.common.properties.delegates.ThreadLocalDelegate
import rx.Observable
import rx.lang.kotlin.PublishSubject
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.jdo.listener.DeleteLifecycleListener
import javax.jdo.listener.InstanceLifecycleEvent
import javax.jdo.listener.StoreLifecycleListener
import kotlin.reflect.KClass

/**
 * Created by rodrigo on 18/10/15.
 */
/*internal*/ class EntityCallbackListener<E : Entity>(
        private val s: Services,
        val entityType: KClass<E>
) : StoreLifecycleListener, DeleteLifecycleListener {

    private val threadLocalCleaner = ThreadLocalCleaner()
    private val publisher = PublishSubject<E>()
    val changes: Observable<E> = publisher.observeOn(Schedulers.computation())
    private var modifiedEntitiesListWasAccessed: Boolean by ThreadLocalDelegate(threadLocalCleaner) { false }
    private val modifiedEntities: MutableList<E> by ThreadLocalDelegate(threadLocalCleaner) {
        modifiedEntitiesListWasAccessed = true
        arrayListOf<E>()
    }

    // Meant to avoid unnecessary empty ArrayList allocation and subsequent iteration
    private var entitiesWereModified: Boolean
        get() = modifiedEntitiesListWasAccessed && modifiedEntities.isNotEmpty()
        set(value: Boolean) {
            modifiedEntitiesListWasAccessed = value
        }

    fun register() {
        s.persistenceContext.registerEntityListener(this)
    }

    fun onTransactionCommitted() {
        if (entitiesWereModified) {
            // Prevent reentrant execution caused by recursion (onNext triggers a new transaction)
            entitiesWereModified = false
            modifiedEntities.forEach { publisher.onNext(it) }
            // No need to remove objects from list as resetLocalState should be called afterwards
        }
    }

    fun resetLocalState() = threadLocalCleaner.cleanUpThreadLocalInstances()

    private fun onModifiedEvent(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        val entity = (event.persistentInstance as E)
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
            val persistenceContext: PersistenceContext
    )
}