package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestRootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.EntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.QueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.JdoEntityRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Module
internal class TestDataParentModule : TestRootEntityModule<TestDataParent>() {

    @Provides @Singleton
    override fun provideType(): Class<TestDataParent> = TestDataParent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = TestDataParentQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<TestDataParent>): EntityRepository<TestDataParent> = repository

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestDataParent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityAsyncListener<TestDataParent>): PersistableObjectAsyncListener<*, *> = listener
}

@Module
internal class TestDataChildModule : EntityModule<TestDataChild> {

    @Provides @Singleton
    override fun provideType(): Class<TestDataChild> = TestDataChild::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestDataChild>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityAsyncListener<TestDataChild>): PersistableObjectAsyncListener<*, *> = listener
}

@Module
internal class TestDataGrandChildModule : EntityModule<TestDataGrandChild> {

    @Provides @Singleton
    override fun provideType(): Class<TestDataGrandChild> = TestDataGrandChild::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<TestDataGrandChild>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityAsyncListener<TestDataGrandChild>): PersistableObjectAsyncListener<*, *> = listener
}