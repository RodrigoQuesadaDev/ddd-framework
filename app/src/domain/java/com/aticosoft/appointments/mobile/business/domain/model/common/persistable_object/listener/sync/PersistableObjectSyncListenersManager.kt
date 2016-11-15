package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 16/10/15.
 */
@Singleton
/*internal*/ class PersistableObjectSyncListenersManager @Inject protected constructor(
        private val objectListeners: MutableSet<PersistableObjectSyncListener<*>>
) {
    private val objectListenerMap: Map<Class<*>, PersistableObjectSyncListener<*>> = objectListeners.associateBy { it.objectType }

    @Suppress("UNCHECKED_CAST")
    fun <P : PersistableObject<*>> forType(type: Class<P>): PersistableObjectSyncListener<P> = objectListenerMap[type]!! as PersistableObjectSyncListener<P>

    fun registerListeners(): Unit = objectListeners.forEach { it.register() }
}