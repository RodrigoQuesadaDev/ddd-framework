package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.event.EventListener
import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer

/**
 * Created by Rodrigo Quesada on 23/08/16.
 */
/*internal*/ interface EventModule<E : Event,
        out Q : Queries<E>, in QI : Q,
        in R : EventRepository<E>,
        in S : EventStore<E>,
        V : PersistableObjectValidator<E, *>,
        I : PersistableObjectInitializer<E>,
        L : EventListener<E>> : RootPersistableObjectModule<E, Q, QI, Enum<*>, R, V, I, L> {

    override fun provideRepository(repository: R): EventRepository<E>

    fun provideEventStore(eventStore: S): EventStore<E>

    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> {
        // Events should not have nested objects, so default view should always be used
        // This override works because (as of this time, check Git history) Dagger 2 doesn't allow
        // @Provides methods to override another method
        throw UnsupportedOperationException()
    }
}