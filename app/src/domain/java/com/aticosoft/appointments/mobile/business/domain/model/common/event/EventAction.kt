package com.aticosoft.appointments.mobile.business.domain.model.common.event

/**
 * Created by Rodrigo Quesada on 28/08/16.
 */
/*internal*/ interface EventAction<E : Event> {
    val eventType: Class<E>
}