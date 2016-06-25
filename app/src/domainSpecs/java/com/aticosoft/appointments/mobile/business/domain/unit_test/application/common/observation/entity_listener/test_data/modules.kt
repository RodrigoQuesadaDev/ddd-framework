package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestRootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.EntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.QueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer
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
        EntityValidator<TestDataParent, *>,
        EntityInitializer<TestDataParent>,
        EntityListener<TestDataParent>>() {

    @Provides @Singleton
    override fun provideEntityType(): Class<TestDataParent> = TestDataParent::class.java

    @Provides @IntoSet
    override fun provideEntityTypeIntoSet(): Class<out Entity> = provideEntityType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = TestDataParentQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: TestDataParentRepository): Repository<TestDataParent> = repository

    @Provides @Singleton
    fun provideObserver(observer: TestDataParentObserver): EntityObserver<TestDataParent> = observer

    @Provides @IntoSet
    override fun provideEntityInitializerIntoSet(initializer: EntityInitializer<TestDataParent>): EntityInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideEntityListenerIntoSet(listener: EntityListener<TestDataParent>): EntityListener<*> = listener
}

@Module
internal class TestDataChildModule : EntityModule<TestDataChild,
        EntityValidator<TestDataChild, *>,
        EntityInitializer<TestDataChild>,
        EntityListener<TestDataChild>> {

    @Provides @Singleton
    override fun provideEntityType(): Class<TestDataChild> = TestDataChild::class.java

    @Provides @IntoSet
    override fun provideEntityTypeIntoSet(): Class<out Entity> = provideEntityType()

    @Provides @IntoSet
    override fun provideEntityInitializerIntoSet(initializer: EntityInitializer<TestDataChild>): EntityInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideEntityListenerIntoSet(listener: EntityListener<TestDataChild>): EntityListener<*> = listener
}