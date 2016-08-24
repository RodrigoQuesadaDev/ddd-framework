package com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 16/10/15.
 */
@Singleton
/*internal*/ class PersistableObjectListenersManager @Inject protected constructor(
        private val objectListeners: MutableSet<PersistableObjectListener<*, *>>
) {
    private val objectListenerMap: Map<Class<*>, PersistableObjectListener<*, *>> = objectListeners.associateBy { it.objectType }

    @Suppress("UNCHECKED_CAST")
    fun <P : PersistableObject<*>> forType(type: Class<P>): PersistableObjectListener<P, *> = objectListenerMap[type]!! as PersistableObjectListener<P, *>

    fun registerListeners(): Unit = objectListeners.forEach { it.register() }
}