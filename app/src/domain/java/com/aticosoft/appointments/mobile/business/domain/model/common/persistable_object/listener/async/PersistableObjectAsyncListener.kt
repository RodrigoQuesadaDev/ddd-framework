package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent.EventType
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.PersistableObjectLifecycleListener
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.properties.Delegates.threadLocal
import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
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
/*internal*/ abstract class PersistableObjectAsyncListener<P : PersistableObject<I>, I> protected constructor()
: PersistableObjectLifecycleListener<P>, PersistableObject.PersistableObjectStateAccess<I>, CreateLifecycleListener, StoreLifecycleListener, DeleteLifecycleListener, DirtyLifecycleListener {

    //TODO create JdoPersistableObjectListenerBase on infrastructure stuff??? (this class pertains to domain)
    //TODO create JdoPersistableObjectChangeEvent on infrastructure stuff??? (this class pertains to domain)

    private lateinit var m: InjectedMembers<P>

    override val objectType: Class<P>
        get() = m.objectType

    private val threadLocalCleaner = ThreadLocalCleaner()
    private val publisher = PublishSubject<PersistableObjectChangeEvent<P>>()
    // Synchronizing/serializing the publisher is not necessary because observeOn is being used (its implementation uses a concurrent queue to dispatch to the scheduler)
    val changes: Observable<PersistableObjectChangeEvent<P>> = publisher.observeOn(Schedulers.computation())
    // Meant to avoid unnecessary empty ArrayList allocation and subsequent iteration
    private val objectChanges: MutableList<PersistableObjectChangeEvent<P>> by threadLocal(threadLocalCleaner) {
        arrayListOf<PersistableObjectChangeEvent<P>>()
    }

    fun register() {
        m.persistenceContext.registerPersistableObjectAsyncListener(this)
    }

    fun resetLocalState() = threadLocalCleaner.cleanUpThreadLocalInstances()

    fun onTransactionCommitted() {
        objectChanges.forEach { publisher.onNext(it) }
        // No need to remove objects from list as resetLocalState should be called afterwards
    }

    override fun preDirty(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        (event.source as P).let { obj ->
            //TODO move code somewhere else...
            //TODO apparently it is only used for EntityObserver... so... probably just leave it here...
            obj.previousValue = m.persistenceContext.persistenceManager.detachCopy(obj)
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

    //region Non-Implemented Methods
    override fun preStore(event: InstanceLifecycleEvent) {
        // Do nothing!
    }

    override fun preDelete(event: InstanceLifecycleEvent) {
        // Do nothing!
    }

    override fun postDirty(event: InstanceLifecycleEvent?) {
        // Do nothing!
    }
    //endregion

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<P>) {
        m = injectedMembers
    }

    protected class InjectedMembers<P : PersistableObject<*>> @Inject protected constructor(
            val persistenceContext: PersistenceContext,
            val objectType: Class<P>
    )
    //endregion
}