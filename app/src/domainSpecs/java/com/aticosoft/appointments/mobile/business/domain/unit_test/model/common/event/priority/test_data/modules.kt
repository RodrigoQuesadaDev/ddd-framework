package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.test_data

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
internal class SamePriorityEventModule : TestEventModule<SamePriorityEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<SamePriorityEvent> = SamePriorityEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Event> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<SamePriorityEvent>): EventRepository<SamePriorityEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<SamePriorityEvent>): EventStore<SamePriorityEvent> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(
            eventAction1: SamePriorityEventAction1,
            eventAction2: SamePriorityEventAction2,
            eventAction3: SamePriorityEventAction3,
            eventAction4: SamePriorityEventAction4,
            eventAction5: SamePriorityEventAction5,
            eventAction6: SamePriorityEventAction6
    ): Set<EventAction<*>> {
        return setOf(eventAction1, eventAction2, eventAction3, eventAction4, eventAction5, eventAction6)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<SamePriorityEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<SamePriorityEvent>): PersistableObjectListener<*, *> = listener
}

@Module
internal class DifferentPriorityEventModule : TestEventModule<DifferentPriorityEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<DifferentPriorityEvent> = DifferentPriorityEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Event> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<DifferentPriorityEvent>): EventRepository<DifferentPriorityEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<DifferentPriorityEvent>): EventStore<DifferentPriorityEvent> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(
            eventAction1: DifferentPriorityEventAction1,
            eventAction2: DifferentPriorityEventAction2,
            eventAction3: DifferentPriorityEventAction3,
            eventAction4: DifferentPriorityEventAction4,
            eventAction5: DifferentPriorityEventAction5
    ): Set<EventAction<*>> {
        return setOf(eventAction1, eventAction2, eventAction3, eventAction4, eventAction5)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<DifferentPriorityEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<DifferentPriorityEvent>): PersistableObjectListener<*, *> = listener
}

@Module
internal class DefaultPriorityEventModule : TestEventModule<DefaultPriorityEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<DefaultPriorityEvent> = DefaultPriorityEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Event> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<DefaultPriorityEvent>): EventRepository<DefaultPriorityEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<DefaultPriorityEvent>): EventStore<DefaultPriorityEvent> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(
            eventAction1: DefaultPriorityEventAction1,
            eventAction2: DefaultPriorityEventAction2,
            eventAction3: DefaultPriorityEventAction3
    ): Set<EventAction<*>> {
        return setOf(eventAction1, eventAction2, eventAction3)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<DefaultPriorityEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<DefaultPriorityEvent>): PersistableObjectListener<*, *> = listener
}