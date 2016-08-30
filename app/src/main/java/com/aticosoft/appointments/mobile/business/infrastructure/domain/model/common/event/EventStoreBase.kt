package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 25/08/16.
 */
@Singleton
/*internal*/ class EventStoreBase<E : Event> @Inject protected constructor(
        private val repository: EventRepository<E>,
        private val eventActions: MutableSet<EventAction<E>>
) : EventStore<E> {

    override fun add(event: E) {
        repository.add(event)
    }

    //region Accessor Interfaces
    interface ActionsAccessor<E : Event> {
        val EventStoreBase<E>.subscribedEventActions: MutableSet<EventAction<E>>
            get() = this.eventActions
    }
    //endregion
}