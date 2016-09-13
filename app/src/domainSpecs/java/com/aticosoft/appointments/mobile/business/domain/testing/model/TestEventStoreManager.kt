package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 28/08/16.
 */
@Singleton
internal class TestEventStoreManager<E : Event> @Inject protected constructor(
        private val eventStore: TestEventStore<E>
) {

    val subscribedActions: Set<EventAction<E>>
        get() = eventStore.eventActionsSet.toSet()

    fun subscribeAction(testEventAction: EventAction<E>) {
        eventStore.eventActionsSet.add(testEventAction)
    }
}