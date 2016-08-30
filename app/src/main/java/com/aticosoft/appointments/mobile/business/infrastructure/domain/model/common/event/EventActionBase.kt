package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 30/08/16.
 */
/*internal*/ abstract class EventActionBase<E : Event> : EventAction<E> {

    private lateinit var m: InjectedMembers<E>

    override final val eventType: Class<E>
        get() = m.eventType

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