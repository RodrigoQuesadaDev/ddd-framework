package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.event.EventAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventStoreBase

/**
 * Created by Rodrigo Quesada on 23/08/16.
 */
/*internal*/ interface EventModule<E : Event, out Q : Queries<E>, in R : EventRepository<E>>
: RootPersistableObjectModule<E, EventAsyncListener<E>, Q, R> {

    override fun provideTypeIntoSet(): Class<out Event>

    override fun provideRepository(repository: R): EventRepository<E>

    fun provideEventStore(eventStore: EventStoreBase<E>): EventStore<E>

    // Events should not have nested objects, so default view should always be used
    // This override works because (as of this time, check Git history) Dagger 2 doesn't allow
    // @Provides methods to override another method
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = throw UnsupportedOperationException()

    fun provideEventActionsIntoSet(): Set<EventAction<*>> = emptySet()
}