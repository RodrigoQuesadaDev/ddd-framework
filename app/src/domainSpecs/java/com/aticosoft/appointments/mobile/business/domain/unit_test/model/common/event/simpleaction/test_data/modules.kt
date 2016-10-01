package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.event.EventListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectListener
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
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
internal class TestEventNoSubsModule : TestEventModule<TestEventNoSubs>() {

    @Provides @Singleton
    override fun provideType(): Class<TestEventNoSubs> = TestEventNoSubs::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<TestEventNoSubs>): EventRepository<TestEventNoSubs> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<TestEventNoSubs>): EventStore<TestEventNoSubs> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(): Set<EventAction<TestEventNoSubs>> = emptySet()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestEventNoSubs>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<TestEventNoSubs>): PersistableObjectListener<*, *> = listener
}

@Module
internal class TestEventThreeSubsModule : TestEventModule<TestEventThreeSubs>() {

    @Provides @Singleton
    override fun provideType(): Class<TestEventThreeSubs> = TestEventThreeSubs::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<TestEventThreeSubs>): EventRepository<TestEventThreeSubs> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<TestEventThreeSubs>): EventStore<TestEventThreeSubs> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(): Set<EventAction<TestEventThreeSubs>> = emptySet()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestEventThreeSubs>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<TestEventThreeSubs>): PersistableObjectListener<*, *> = listener
}

@Module
internal class TestEventFiveSubsModule : TestEventModule<TestEventFiveSubs>() {

    @Provides @Singleton
    override fun provideType(): Class<TestEventFiveSubs> = TestEventFiveSubs::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<TestEventFiveSubs>): EventRepository<TestEventFiveSubs> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<TestEventFiveSubs>): EventStore<TestEventFiveSubs> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(): Set<EventAction<TestEventFiveSubs>> = emptySet()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestEventFiveSubs>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<TestEventFiveSubs>): PersistableObjectListener<*, *> = listener
}