package com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import javax.jdo.listener.InstanceLifecycleEvent

/**
 * Created by Rodrigo Quesada on 10/11/15.
 */
/*internal*/ class PersistableObjectChangeEvent<out P : PersistableObject<*>>(val type: EventType, val previousValue: P? = null, val currentValue: P? = null) {

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