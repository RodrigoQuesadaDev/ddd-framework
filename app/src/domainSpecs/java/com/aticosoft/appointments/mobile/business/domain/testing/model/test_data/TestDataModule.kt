package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestRootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Module
internal class TestDataModule : TestRootEntityModule<TestData,
        TestDataQueries, TestDataQueries,
        TestDataQueryView,
        TestDataRepository,
        EntityValidator<TestData, *>,
        EntityInitializer<TestData>,
        EntityListener<TestData>>() {

    @Provides @Singleton
    override fun provideEntityType(): Class<TestData> = TestData::class.java

    @Provides @IntoSet
    override fun provideEntityTypeIntoSet(): Class<out Entity> = provideEntityType()

    @Provides @IntoSet
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = TestDataQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: TestDataRepository): Repository<TestData> = repository

    @Provides @IntoSet
    override fun provideEntityInitializerIntoSet(initializer: EntityInitializer<TestData>): EntityInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideEntityListenerIntoSet(listener: EntityListener<TestData>): EntityListener<*> = listener
}