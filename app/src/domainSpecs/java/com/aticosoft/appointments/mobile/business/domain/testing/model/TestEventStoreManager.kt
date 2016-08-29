package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 28/08/16.
 */
@Singleton
internal class TestEventStoreManager<E : Event> @Inject protected constructor(
        private val eventStore: EventStore<E>
) {
    val subscribedActions: List<EventAction<E>> = emptyList()
}