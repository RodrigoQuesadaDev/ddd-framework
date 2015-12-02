package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.observation.EntityListeners
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 16/10/15.
 */
@Singleton
/*internal*/ class EntityListenersManager @Inject constructor(
        @EntityListeners private val entityListeners: Array<EntityListener<*>>
) {
    private val entityListenerMap: Map<Class<*>, EntityListener<*>> = entityListeners.toMapBy { it.entityType }

    fun forType(type: Class<*>): EntityListener<*> = entityListenerMap[type]!!

    fun registerListeners() {
        entityListeners.forEach { it.register() }
    }
}