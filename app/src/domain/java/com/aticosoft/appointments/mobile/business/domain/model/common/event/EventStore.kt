package com.aticosoft.appointments.mobile.business.domain.model.common.event

/**
 * Created by Rodrigo Quesada on 24/09/15.
 */
/*internal*/ interface EventStore<E : Event> {

    fun add(event: E)
}