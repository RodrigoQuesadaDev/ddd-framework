package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent.EventType
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import javax.jdo.listener.InstanceLifecycleEvent

/**
 * Created by Rodrigo Quesada on 10/11/15.
 */
/*internal*/ class EntityChangeEvent<E : Entity>(val type: EventType, val previousValue: E? = null, val currentValue: E? = null) {

    enum class EventType { ADD, UPDATE, REMOVE;

        companion object {
            fun from(constant: Int): EventType = when (constant) {
                InstanceLifecycleEvent.CREATE -> ADD
                InstanceLifecycleEvent.STORE -> UPDATE
                InstanceLifecycleEvent.DELETE -> REMOVE
                else -> throw IllegalArgumentException("Invalid constant value \"$constant\" for event type.")
            }
        }
    }
}