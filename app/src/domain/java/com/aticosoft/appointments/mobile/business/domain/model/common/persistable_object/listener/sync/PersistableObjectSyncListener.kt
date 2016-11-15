@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject.PersistableObjectStateAccess
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.PersistableObjectLifecycleListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.ObjectChangeType.UPDATE
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.collection.unmodifiable
import com.rodrigodev.common.properties.Delegates.postInitialized
import com.rodrigodev.common.properties.UnsafePostInitialized
import com.rodrigodev.common.properties.delegates.UnsafePostInitializedPropertyDelegate.UnsafePropertyInitializer
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.JDOHelper
import javax.jdo.listener.*

/**
 * Created by Rodrigo Quesada on 18/10/15.
 */
@Singleton
/*internal*/ class PersistableObjectSyncListener<P : PersistableObject<*>> @Inject protected constructor()
: PersistableObjectLifecycleListener<P>, PersistableObjectStateAccess, CreateLifecycleListener, StoreLifecycleListener, DeleteLifecycleListener, DirtyLifecycleListener {

    private lateinit var m: InjectedMembers<P>

    override val objectType: Class<P>
        get() = m.objectType

    private val addSubscriptions = Subscriptions<P>()
    private val updateSubscriptions = Subscriptions<P>()
    private val removeSubscriptions = Subscriptions<P>()

    fun register() {
        m.persistenceContext.registerPersistableObjectLifecycleListener(this)
        arrayOf(addSubscriptions, updateSubscriptions, removeSubscriptions).forEach { it.finalize() }
    }

    fun subscribe(subscription: Subscription<P>) {
        subscription.changeTypes.forEach { it.subscriptions.add(subscription) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun preDirty(event: InstanceLifecycleEvent) = (event.source as P).let { obj ->
        obj.previousValue = m.persistenceContext.persistenceManager.detachCopy(obj)
    }

    @Suppress("UNCHECKED_CAST")
    override fun postCreate(event: InstanceLifecycleEvent) = addSubscriptions.invoke(event.source as P)

    override fun preStore(event: InstanceLifecycleEvent) {
        //only updates
        if (!JDOHelper.isNew(event.source)) {
            @Suppress("UNCHECKED_CAST")
            updateSubscriptions.invoke(event.source as P)
        }
    }

    override fun preDelete(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        removeSubscriptions.invoke(event.source as P)
    }

    //region Non-Implemented Lifecycle Methods
    override fun postStore(event: InstanceLifecycleEvent) {
        // Do nothing!
    }

    override fun postDelete(event: InstanceLifecycleEvent) {
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

    //region Subscriptions
    interface Subscription<P : PersistableObject<*>> {

        abstract val changeTypes: Array<out ObjectChangeType>

        abstract fun trigger(obj: P)
    }

    abstract class SimpleSubscription<P : PersistableObject<*>>(
            override val changeTypes: Array<out ObjectChangeType>
    ) : Subscription<P> {

        abstract fun callback(value: P): Unit

        override fun trigger(obj: P) = callback(obj)
    }

    abstract class PreviousValueSubscription<P : PersistableObject<*>>(
            override final val changeTypes: Array<out ObjectChangeType>
    ) : Subscription<P>, PersistableObjectStateAccess {

        init {
            require(changeTypes.single() == UPDATE, { "Currently only the UPDATE ObjectChangeType is supported for PreviousValueSubscription objects." })
        }

        abstract fun callback(previousValue: P, currentValue: P): Unit

        @Suppress("UNCHECKED_CAST")
        override fun trigger(obj: P) = callback(obj.previousValue as P, obj)
    }

    private class Subscriptions<P : PersistableObject<*>> : UnsafePostInitialized {

        override val _propertyInitializer = UnsafePropertyInitializer()

        private var tempList: MutableList<Subscription<P>> = mutableListOf()

        private val list: List<Subscription<P>> by postInitialized { tempList }

        fun finalize() {
            tempList = tempList.unmodifiable()
            _postInit()
        }

        fun add(subscription: Subscription<P>) {
            tempList.add(subscription)
        }

        fun invoke(obj: P) {
            list.forEach { it.trigger(obj) }
        }
    }

    private val ObjectChangeType.subscriptions: Subscriptions<P>
        get() = when (this) {
            ObjectChangeType.ADD -> addSubscriptions
            UPDATE -> updateSubscriptions
            ObjectChangeType.REMOVE -> removeSubscriptions
        }
    //endregion
}