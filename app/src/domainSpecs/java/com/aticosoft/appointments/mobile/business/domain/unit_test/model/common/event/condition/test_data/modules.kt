package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.condition.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.event.EventListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectListener
import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
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
            eventAction3: SampleEventAction3
    ): Set<EventAction<*>> {
        return setOf(eventAction1, eventAction2, eventAction3)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<SampleEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<SampleEvent>): PersistableObjectListener<*, *> = listener
}

@Module
internal class BadCondModifiesEventModule : TestEventModule<BadCondModifiesEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<BadCondModifiesEvent> = BadCondModifiesEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Event> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<BadCondModifiesEvent>): EventRepository<BadCondModifiesEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<BadCondModifiesEvent>): EventStore<BadCondModifiesEvent> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(
            eventAction1: BadCondModifiesEventAction1
    ): Set<EventAction<*>> {
        return setOf(eventAction1)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<BadCondModifiesEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<BadCondModifiesEvent>): PersistableObjectListener<*, *> = listener
}

@Module
internal class BadCondAddedAfterInitEventModule : TestEventModule<BadCondAddedAfterInitEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<BadCondAddedAfterInitEvent> = BadCondAddedAfterInitEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Event> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<BadCondAddedAfterInitEvent>): EventRepository<BadCondAddedAfterInitEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<BadCondAddedAfterInitEvent>): EventStore<BadCondAddedAfterInitEvent> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(
            eventAction1: BadCondAddedAfterInitEventAction1
    ): Set<EventAction<*>> {
        return setOf(eventAction1)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<BadCondAddedAfterInitEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<BadCondAddedAfterInitEvent>): PersistableObjectListener<*, *> = listener
}
