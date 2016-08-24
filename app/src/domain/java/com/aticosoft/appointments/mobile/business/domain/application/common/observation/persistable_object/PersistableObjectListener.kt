package com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent.EventType
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObjectLifecycleListener
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
import com.rodrigodev.common.properties.delegates.ThreadLocalDelegate
import rx.Observable
import rx.lang.kotlin.PublishSubject
import rx.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.JDOHelper
import javax.jdo.listener.*

/**
 * Created by Rodrigo Quesada on 18/10/15.
 */
@Singleton
/*internal*/ abstract class PersistableObjectListener<P : PersistableObject<I>, I> protected constructor(
        private val s: Services,
        override val objectType: Class<P>
) : PersistableObjectLifecycleListener<P>, PersistableObject.PersistableObjectStateAccess<I>, CreateLifecycleListener, StoreLifecycleListener, DeleteLifecycleListener, DirtyLifecycleListener {

    //TODO create JdoPersistableObjectListenerBase on infrastructure stuff??? (this class pertains to domain)
    //TODO create JdoPersistableObjectChangeEvent on infrastructure stuff??? (this class pertains to domain)

    private val threadLocalCleaner = ThreadLocalCleaner()
    private val publisher = PublishSubject<PersistableObjectChangeEvent<P>>()
    val changes: Observable<PersistableObjectChangeEvent<P>> = publisher.observeOn(Schedulers.computation())
    // Meant to avoid unnecessary empty ArrayList allocation and subsequent iteration
    private var newObjectChanges: Boolean by ThreadLocalDelegate(threadLocalCleaner) { false }
    private val objectChanges: MutableList<PersistableObjectChangeEvent<P>> by ThreadLocalDelegate(threadLocalCleaner) {
        newObjectChanges = true
        arrayListOf<PersistableObjectChangeEvent<P>>()
    }

    fun register() {
        s.persistenceContext.registerPersistableObjectListener(this)
    }

    fun resetLocalState() = threadLocalCleaner.cleanUpThreadLocalInstances()

    fun onTransactionCommitted() {
        if (newObjectChanges) {
            // Prevent reentrant execution caused by recursion (onNext triggers a new transaction)
            newObjectChanges = false
            objectChanges.forEach { publisher.onNext(it) }
            // No need to remove objects from list as resetLocalState should be called afterwards
        }
    }

    override fun preDirty(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        (event.source as P).let { obj ->
            obj.previousValue = s.persistenceContext.persistenceManager.detachCopy(obj)
        }
    }

    override fun postCreate(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        objectChanges.add(PersistableObjectChangeEvent(EventType.Companion.from(event.eventType), currentValue = event.source as P))
    }

    override fun postStore(event: InstanceLifecycleEvent) {
        //only updates
        if (!JDOHelper.isNew(event.source)) {
            @Suppress("UNCHECKED_CAST")
            (event.source as P).let { obj ->
                objectChanges.add(PersistableObjectChangeEvent(EventType.Companion.from(event.eventType), previousValue = obj.previousValue as P, currentValue = obj))
            }
        }
    }

    override fun postDelete(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        objectChanges.add(PersistableObjectChangeEvent(EventType.Companion.from(event.eventType), previousValue = event.source as P))
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