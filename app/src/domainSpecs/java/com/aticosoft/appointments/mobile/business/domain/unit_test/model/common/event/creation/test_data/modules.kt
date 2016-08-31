package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.event.EventListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectListener
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestEventModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventStoreBase
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
internal class NoSubscriptionsEventModule : TestEventModule<NoSubscriptionsEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<NoSubscriptionsEvent> = NoSubscriptionsEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<NoSubscriptionsEvent>): EventRepository<NoSubscriptionsEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: EventStoreBase<NoSubscriptionsEvent>): EventStore<NoSubscriptionsEvent> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(): Set<EventAction<NoSubscriptionsEvent>> = emptySet()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<NoSubscriptionsEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<NoSubscriptionsEvent>): PersistableObjectListener<*, *> = listener
}

@Module
internal class OneSubscriptionEventModule : TestEventModule<OneSubscriptionEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<OneSubscriptionEvent> = OneSubscriptionEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<OneSubscriptionEvent>): EventRepository<OneSubscriptionEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: EventStoreBase<OneSubscriptionEvent>): EventStore<OneSubscriptionEvent> = eventStore

    @Provides @IntoSet
    fun provideEventActionsIntoSet(eventAction: OneSubscriptionEventAction1): EventAction<OneSubscriptionEvent> = eventAction

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<OneSubscriptionEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<OneSubscriptionEvent>): PersistableObjectListener<*, *> = listener
}

@Module
internal class FiveSubscriptionsEventModule : TestEventModule<FiveSubscriptionsEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<FiveSubscriptionsEvent> = FiveSubscriptionsEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<FiveSubscriptionsEvent>): EventRepository<FiveSubscriptionsEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: EventStoreBase<FiveSubscriptionsEvent>): EventStore<FiveSubscriptionsEvent> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(
            eventAction1: FiveSubscriptionsEventAction1,
            eventAction2: FiveSubscriptionsEventAction2,
            eventAction3: FiveSubscriptionsEventAction3,
            eventAction4: FiveSubscriptionsEventAction4,
            eventAction5: FiveSubscriptionsEventAction5
    ): Set<EventAction<FiveSubscriptionsEvent>> {
        return setOf(eventAction1, eventAction2, eventAction3, eventAction4, eventAction5)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<FiveSubscriptionsEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<FiveSubscriptionsEvent>): PersistableObjectListener<*, *> = listener
}