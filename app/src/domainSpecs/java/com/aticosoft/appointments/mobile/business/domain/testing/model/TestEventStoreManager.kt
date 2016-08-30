package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventStoreBase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 28/08/16.
 */
@Singleton
internal class TestEventStoreManager<E : Event> @Inject protected constructor(
        private val eventStore: EventStoreBase<E>
) : EventStoreBase.ActionsAccessor<E> {

    val subscribedActions: Set<EventAction<E>>
        get() = eventStore.subscribedEventActions
}