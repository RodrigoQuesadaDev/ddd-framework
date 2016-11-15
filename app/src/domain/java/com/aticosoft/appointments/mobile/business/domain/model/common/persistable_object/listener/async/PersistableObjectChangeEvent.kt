package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject

/**
 * Created by Rodrigo Quesada on 10/11/15.
 */
/*internal*/ class PersistableObjectChangeEvent<out P : PersistableObject<*>>(val type: EventType, val previousValue: P? = null, val currentValue: P? = null) {

    enum class EventType { ADD, UPDATE, REMOVE }
}