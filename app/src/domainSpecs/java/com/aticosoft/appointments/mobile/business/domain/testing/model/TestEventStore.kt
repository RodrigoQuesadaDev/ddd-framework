package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.SimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEventAction
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventStoreBase
import com.rodrigodev.common.properties.Delegates.postInitialized
import com.rodrigodev.common.rx.testing.triggerTestSchedulerActions
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 01/09/16.
 */
@Singleton
internal class TestEventStore<E : Event> @Inject protected constructor() : EventStoreBase<E>() {

    public override val simpleActions: List<SimpleEventAction<E>> by postInitialized {
        super.simpleActions.apply {
            forEachIndexed { i, action ->
                if (action is TestEventAction<*>) action.init(i)
            }
        }
    }

    fun suspendActionsExecution() {
        actionsSubscription!!.unsubscribe()
    }

    fun resumeActionsExecution() {
        resubscribeActions()
        triggerTestSchedulerActions()
    }
}