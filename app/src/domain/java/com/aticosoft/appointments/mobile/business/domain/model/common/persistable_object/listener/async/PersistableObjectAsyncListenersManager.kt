package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 16/10/15.
 */
@Singleton
/*internal*/ class PersistableObjectAsyncListenersManager @Inject protected constructor(
        private val objectListeners: MutableSet<PersistableObjectAsyncListener<*, *>>
) {
    private val objectListenerMap: Map<Class<*>, PersistableObjectAsyncListener<*, *>> = objectListeners.associateBy { it.objectType }

    @Suppress("UNCHECKED_CAST")
    fun <P : PersistableObject<*>> forType(type: Class<P>): PersistableObjectAsyncListener<P, *> = objectListenerMap[type]!! as PersistableObjectAsyncListener<P, *>

    fun registerListeners(): Unit = objectListeners.forEach { it.register() }
}