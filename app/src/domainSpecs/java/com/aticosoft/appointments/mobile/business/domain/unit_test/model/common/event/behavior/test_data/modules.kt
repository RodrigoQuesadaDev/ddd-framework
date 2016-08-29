package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.behavior.test_data

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
internal class TestEventAModule : TestEventModule<TestEventA,
        PersistableObjectValidator<TestEventA, *>,
        PersistableObjectInitializer<TestEventA>,
        EventListener<TestEventA>>() {

    @Provides @Singleton
    override fun provideType(): Class<TestEventA> = TestEventA::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<TestEventA>): EventRepository<TestEventA> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: JdoEventStore<TestEventA>): EventStore<TestEventA> = eventStore

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestEventA>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<TestEventA>): PersistableObjectListener<*, *> = listener
}

@Module
internal class TestEventBModule : TestEventModule<TestEventB,
        PersistableObjectValidator<TestEventB, *>,
        PersistableObjectInitializer<TestEventB>,
        EventListener<TestEventB>>() {

    @Provides @Singleton
    override fun provideType(): Class<TestEventB> = TestEventB::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideRepository(repository: JdoEventRepository<TestEventB>): EventRepository<TestEventB> = repository

    @Provides @Singleton
    override fun provideEventStore(eventStore: JdoEventStore<TestEventB>): EventStore<TestEventB> = eventStore

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestEventB>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EventListener<TestEventB>): PersistableObjectListener<*, *> = listener
}
