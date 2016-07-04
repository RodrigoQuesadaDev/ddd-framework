package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 16/10/15.
 */
@Singleton
/*internal*/ class EntityListenersManager @Inject protected constructor(
        private val entityListeners: MutableSet<EntityListener<*>>
) {
    private val entityListenerMap: Map<Class<*>, EntityListener<*>> = entityListeners.associateBy { it.entityType }

    @Suppress("UNCHECKED_CAST")
    fun <E : Entity> forType(type: Class<E>): EntityListener<E> = entityListenerMap[type]!! as EntityListener<E>

    fun registerListeners(): Unit = entityListeners.forEach { it.register() }
}