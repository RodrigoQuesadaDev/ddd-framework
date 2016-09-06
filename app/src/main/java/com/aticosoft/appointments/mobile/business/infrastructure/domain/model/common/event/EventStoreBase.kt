package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.event.EventInternalObserver
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
/*internal*/ open class EventStoreBase<E : Event> @Inject protected constructor() : EventStore<E> {

    private lateinit var m: InjectedMembers<E>

    protected open val eventActions: Set<EventAction<E>>
        get() = m.eventActions

    private fun init() {
        //m.eventObserver.observe()
    }

    override fun add(event: E) {
        m.repository.add(event)
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<E>) {
        m = injectedMembers
        init()
    }

    protected class InjectedMembers<E : Event> @Inject protected constructor(
            val repository: EventRepository<E>,
            val eventActions: MutableSet<EventAction<E>>,
            val eventObserver: EventInternalObserver<E>
    )
    //endregion
}