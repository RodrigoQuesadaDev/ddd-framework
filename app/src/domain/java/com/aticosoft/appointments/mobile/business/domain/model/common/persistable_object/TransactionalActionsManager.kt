@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.PersistableObjectSyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.PersistableObjectSyncListener.PreviousValueSubscription
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.PersistableObjectSyncListener.SimpleSubscription
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.PersistableObjectSyncListenersManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 04/11/16.
 */
@Singleton
/*internal*/ open class TransactionalActionsManager @Inject protected constructor() {

    private lateinit var m: InjectedMembers

    protected open val actions: MutableSet<AbstractTransactionalAction<*>>
        get() = m.actions

    fun subscribeActions(): Unit = with(m) {
        actions.forEach { persistableObjectSyncListenersManager.forType(it.objectType).subscribe(it) }
    }

    private inline fun <P : PersistableObject<*>> PersistableObjectSyncListener<P>.subscribe(nonCastedAction: AbstractTransactionalAction<*>) {
        @Suppress("UNCHECKED_CAST")
        val action: AbstractTransactionalAction<P> = nonCastedAction as AbstractTransactionalAction<P>

        subscribe(when (action) {
            is TransactionalAction<P> -> object : SimpleSubscription<P>(action.changeTypes) {

                override fun callback(value: P) = action.execute(value)
            }
            is TransactionalUpdateAction<P> -> object : PreviousValueSubscription<P>(action.changeTypes) {

                override fun callback(previousValue: P, currentValue: P) = action.execute(previousValue, currentValue)
            }
            else -> throw IllegalStateException("Invalid action type: ${action.javaClass}")
        })
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers) {
        m = injectedMembers
    }

    protected class InjectedMembers @Inject constructor(
            val actions: MutableSet<AbstractTransactionalAction<*>>,
            val persistableObjectSyncListenersManager: PersistableObjectSyncListenersManager
    )
    //endregion
}