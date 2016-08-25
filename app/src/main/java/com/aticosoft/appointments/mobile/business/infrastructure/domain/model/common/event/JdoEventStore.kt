package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 25/08/16.
 */
@Singleton
/*internal*/ class JdoEventStore<E : Event> @Inject protected constructor() : EventStore<E> {

    private lateinit var m: InjectedMembers<E>

    override fun add(event: E) {
        m.repository.add(event)
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<E>) {
        m = injectedMembers
    }

    protected class InjectedMembers<E : Event> @Inject protected constructor(
            val repository: EventRepository<E>
    )
    //endregion
}