package com.aticosoft.appointments.mobile.business.domain.model.common.event

/**
 * Created by Rodrigo Quesada on 28/08/16.
 */

/**
 * Actions should be idempotent with regards to the same input (that is, if the same event with the
 * same values is process again, the result is the same).
 */
/*internal*/ interface EventAction<E : Event> {
    companion object {
        val DEFAULT_PRIORITY = 0
    }

    //TODO local and local-ephemeral data???

    val eventType: Class<E>
    val priority: Int

    val timesReceived: TimesReceivedEvaluator

    fun init()

    fun E.conditionIsMet(state: EventActionState): Boolean
}

interface SimpleEventAction<E : Event> : EventAction<E> {
    fun execute(event: E)
}

interface OverridableEventAction<E : Event> : EventAction<E> {
    fun execute()
}

enum class TimesReceivedEvaluator {
    SINGLE_TIME {
        override fun conditionIsMetFor(action: EventActionState) = action.executionCount == 0
    },

    MULTIPLE_TIMES {
        override fun conditionIsMetFor(action: EventActionState) = true
    };

    abstract fun conditionIsMetFor(action: EventActionState): Boolean
}
