package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction.Companion.DEFAULT_PRIORITY
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventActionState
import com.aticosoft.appointments.mobile.business.domain.model.common.event.TimesReceivedEvaluator
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 30/08/16.
 */
/*internal*/ abstract class EventActionBase<E : Event>(override val timesReceived: TimesReceivedEvaluator) : EventAction<E> {

    private lateinit var m: InjectedMembers<E>

    override final val eventType: Class<E>
        get() = m.eventType

    override val priority = DEFAULT_PRIORITY

    private val conditionClosures: MutableList<E.() -> Boolean> = mutableListOf()

    protected fun condition(closure: E.() -> Boolean) {
        conditionClosures.add(closure)
    }

    override fun E.conditionIsMet(state: EventActionState): Boolean {
        return timesReceived.conditionIsMetFor(state) && conditionClosures.all { it() }
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<E>) {
        m = injectedMembers
    }

    protected class InjectedMembers<E : Event> @Inject protected constructor(
            val eventType: Class<E>
    )
    //endregion
}