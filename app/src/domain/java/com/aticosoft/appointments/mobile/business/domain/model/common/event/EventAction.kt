package com.aticosoft.appointments.mobile.business.domain.model.common.event

/**
 * Created by Rodrigo Quesada on 28/08/16.
 */

/**
 * Actions should be idempotent with regards to the same input (that is, if the same event with the
 * same values is process again, the result is the same).
 */
/*internal*/ interface EventAction<E : Event> {
    val eventType: Class<E>
}

interface SimpleEventAction<E : Event> : EventAction<E> {
    fun execute(event: E)
}

interface OverridableEventAction<E : Event> : EventAction<E> {
    fun execute()
}