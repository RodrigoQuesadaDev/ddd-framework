package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.behavior.test_data

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
internal class TestEventAModule : TestEventModule<TestEventA>() {

    @Provides @Singleton
    override fun provideType(): Class<TestEventA> = TestEventA::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<TestEventA>): EventRepository<TestEventA> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<TestEventA>): EventStore<TestEventA> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(): Set<EventAction<TestEventA>> = emptySet()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestEventA>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<TestEventA>): PersistableObjectListener<*, *> = listener
}

@Module
internal class TestEventBModule : TestEventModule<TestEventB>() {

    @Provides @Singleton
    override fun provideType(): Class<TestEventB> = TestEventB::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<TestEventB>): EventRepository<TestEventB> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: TestEventStore<TestEventB>): EventStore<TestEventB> = eventStore

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(): Set<EventAction<TestEventB>> = emptySet()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestEventB>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<TestEventB>): PersistableObjectListener<*, *> = listener
}
