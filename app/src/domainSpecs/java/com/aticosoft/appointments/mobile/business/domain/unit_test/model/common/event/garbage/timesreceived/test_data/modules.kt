package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.garbage.timesreceived.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.PersistableObjectSyncListener
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestEventModule
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStore
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.JdoEventRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 25/08/16.
 */
@Module
internal class SampleEventModule : TestEventModule<SampleEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<SampleEvent> = SampleEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Event> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<SampleEvent>): EventRepository<SampleEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<SampleEvent>): EventStore<SampleEvent> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(
            eventAction1: SampleEventAction1,
            eventAction2: SampleEventAction2,
            eventAction3: SampleEventAction3,
            eventAction4: SampleEventAction4,
            eventAction5: SampleEventAction5
    ): Set<EventAction<*>> {
        return setOf(eventAction1, eventAction2, eventAction3, eventAction4, eventAction5)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<SampleEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<SampleEvent>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<SampleEvent>): PersistableObjectAsyncListener<*> = listener
}