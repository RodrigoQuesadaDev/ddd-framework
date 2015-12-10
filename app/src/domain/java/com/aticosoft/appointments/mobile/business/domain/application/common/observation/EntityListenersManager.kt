package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.infrastructure.domain.application.common.observation.EntityListeners
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 16/10/15.
 */
@Singleton
/*internal*/ class EntityListenersManager @Inject protected constructor(
        @EntityListeners private val entityListeners: Array<EntityListener<*>>
) {
    private val entityListenerMap: Map<Class<*>, EntityListener<*>> = entityListeners.toMapBy { it.entityType }

    @Suppress("UNCHECKED_CAST")
    fun <E : Entity> forType(type: Class<E>): EntityListener<E> = entityListenerMap[type]!! as EntityListener<E>

    fun registerListeners() {
        entityListeners.forEach { it.register() }
    }
}