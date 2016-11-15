package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.PersistableObjectSyncListener
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestRootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.QueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.ValueObjects
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.JdoEntityRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */
@Module
internal class EntityWithValueObjectModule : TestRootEntityModule<EntityWithValueObject>() {

    @Provides @Singleton
    override fun provideType(): Class<EntityWithValueObject> = EntityWithValueObject::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = EntityWithValueObjectQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<EntityWithValueObject>): EntityRepository<EntityWithValueObject> = repository

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<EntityWithValueObject>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<EntityWithValueObject>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<EntityWithValueObject>): PersistableObjectAsyncListener<*> = listener

    @Provides @ElementsIntoSet @ValueObjects
    fun provideValueObjects(): Set<Class<*>> = setOf(TestValueObject::class.java)
}
