package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.event.EventListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectListener
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestEventModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.JdoEventRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.JdoEventStore
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 25/08/16.
 */
@Module
internal class NoSubscriptionsEventModule : TestEventModule<NoSubscriptionsEvent,
        PersistableObjectValidator<NoSubscriptionsEvent, *>,
        PersistableObjectInitializer<NoSubscriptionsEvent>,
        EventListener<NoSubscriptionsEvent>>() {

    @Provides @Singleton
    override fun provideType(): Class<NoSubscriptionsEvent> = NoSubscriptionsEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<NoSubscriptionsEvent>): EventRepository<NoSubscriptionsEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: JdoEventStore<NoSubscriptionsEvent>): EventStore<NoSubscriptionsEvent> = eventStore

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<NoSubscriptionsEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<NoSubscriptionsEvent>): PersistableObjectListener<*, *> = listener
}

@Module
internal class OneSubscriptionEventModule : TestEventModule<OneSubscriptionEvent,
        PersistableObjectValidator<OneSubscriptionEvent, *>,
        PersistableObjectInitializer<OneSubscriptionEvent>,
        EventListener<OneSubscriptionEvent>>() {

    @Provides @Singleton
    override fun provideType(): Class<OneSubscriptionEvent> = OneSubscriptionEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<OneSubscriptionEvent>): EventRepository<OneSubscriptionEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: JdoEventStore<OneSubscriptionEvent>): EventStore<OneSubscriptionEvent> = eventStore

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<OneSubscriptionEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<OneSubscriptionEvent>): PersistableObjectListener<*, *> = listener
}

@Module
internal class FiveSubscriptionsEventModule : TestEventModule<FiveSubscriptionsEvent,
        PersistableObjectValidator<FiveSubscriptionsEvent, *>,
        PersistableObjectInitializer<FiveSubscriptionsEvent>,
        EventListener<FiveSubscriptionsEvent>>() {

    @Provides @Singleton
    override fun provideType(): Class<FiveSubscriptionsEvent> = FiveSubscriptionsEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<FiveSubscriptionsEvent>): EventRepository<FiveSubscriptionsEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: JdoEventStore<FiveSubscriptionsEvent>): EventStore<FiveSubscriptionsEvent> = eventStore

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<FiveSubscriptionsEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<FiveSubscriptionsEvent>): PersistableObjectListener<*, *> = listener
}