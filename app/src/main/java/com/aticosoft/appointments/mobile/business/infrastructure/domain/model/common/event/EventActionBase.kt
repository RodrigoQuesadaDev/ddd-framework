package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction.Companion.DEFAULT_PRIORITY
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventActionState
import com.aticosoft.appointments.mobile.business.domain.model.common.event.TimesReceivedEvaluator
import java.util.*
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 30/08/16.
 */
/*internal*/ abstract class EventActionBase<E : Event>(override val timesReceived: TimesReceivedEvaluator) : EventAction<E> {

    private lateinit var m: InjectedMembers<E>

    override final val eventType: Class<E>
        get() = m.eventType

    override val priority = DEFAULT_PRIORITY

    private var conditionClosures: MutableList<E.() -> Boolean> = mutableListOf()
    private var initialized = false

    protected fun condition(closure: E.() -> Boolean) {
        check(!initialized, { "Conditions cannot be added after an event action has already been initialized." })
        conditionClosures.add(closure)
    }

    override fun init() {
        conditionClosures = Collections.unmodifiableList(conditionClosures)
        initialized = true
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