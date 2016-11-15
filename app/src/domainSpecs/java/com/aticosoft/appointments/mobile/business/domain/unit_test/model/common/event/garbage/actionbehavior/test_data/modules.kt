package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.garbage.actionbehavior.test_data

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
internal class NoSubscriptionsEventModule : TestEventModule<NoActionsEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<NoActionsEvent> = NoActionsEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Event> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<NoActionsEvent>): EventRepository<NoActionsEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<NoActionsEvent>): EventStore<NoActionsEvent> = eventStore

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<NoActionsEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<NoActionsEvent>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<NoActionsEvent>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class ThreeSubscriptionsEventModule : TestEventModule<ThreeActionsEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<ThreeActionsEvent> = ThreeActionsEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Event> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<ThreeActionsEvent>): EventRepository<ThreeActionsEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<ThreeActionsEvent>): EventStore<ThreeActionsEvent> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(
            eventAction1: ThreeSubscriptionsEventAction1,
            eventAction2: ThreeSubscriptionsEventAction2,
            eventAction3: ThreeSubscriptionsEventAction3
    ): Set<EventAction<*>> {
        return setOf(eventAction1, eventAction2, eventAction3)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<ThreeActionsEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<ThreeActionsEvent>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<ThreeActionsEvent>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class FiveSubscriptionsEventModule : TestEventModule<FiveActionsEvent>() {

    @Provides @Singleton
    override fun provideType(): Class<FiveActionsEvent> = FiveActionsEvent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Event> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<FiveActionsEvent>): EventRepository<FiveActionsEvent> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<FiveActionsEvent>): EventStore<FiveActionsEvent> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(
            eventAction1: FiveSubscriptionsEventAction1,
            eventAction2: FiveSubscriptionsEventAction2,
            eventAction3: FiveSubscriptionsEventAction3,
            eventAction4: FiveSubscriptionsEventAction4,
            eventAction5: FiveSubscriptionsEventAction5
    ): Set<EventAction<*>> {
        return setOf(eventAction1, eventAction2, eventAction3, eventAction4, eventAction5)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<FiveActionsEvent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<FiveActionsEvent>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<FiveActionsEvent>): PersistableObjectAsyncListener<*> = listener
}