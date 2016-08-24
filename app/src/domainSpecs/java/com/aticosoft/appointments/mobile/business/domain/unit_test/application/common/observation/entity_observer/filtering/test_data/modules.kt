package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectListener
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestRootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.EntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.QueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Module
internal class TestDataParentModule : TestRootEntityModule<TestDataParent,
        TestDataParentQueries, TestDataParentQueries,
        TestDataParentQueryView,
        TestDataParentRepository,
        PersistableObjectValidator<TestDataParent, *>,
        PersistableObjectInitializer<TestDataParent>,
        EntityListener<TestDataParent>>() {

    @Provides @Singleton
    override fun provideType(): Class<TestDataParent> = TestDataParent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = TestDataParentQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: TestDataParentRepository): EntityRepository<TestDataParent> = repository

    @Provides @Singleton
    fun provideObserver(observer: TestDataParentObserver): EntityObserver<TestDataParent> = observer

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestDataParent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityListener<TestDataParent>): PersistableObjectListener<*, *> = listener
}

@Module
internal class TestDataChildModule : EntityModule<TestDataChild,
        PersistableObjectValidator<TestDataChild, *>,
        PersistableObjectInitializer<TestDataChild>,
        EntityListener<TestDataChild>> {

    @Provides @Singleton
    override fun provideType(): Class<TestDataChild> = TestDataChild::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestDataChild>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityListener<TestDataChild>): PersistableObjectListener<*, *> = listener
}