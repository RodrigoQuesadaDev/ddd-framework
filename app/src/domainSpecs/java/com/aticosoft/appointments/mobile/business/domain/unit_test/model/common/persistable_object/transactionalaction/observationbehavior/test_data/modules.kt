package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.AbstractTransactionalAction
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.PersistableObjectSyncListener
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestRootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.QueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.JdoEntityRepository
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
internal class ParentSingleEntityModule : TestRootEntityModule<ParentSingleEntity>() {

    @Provides @Singleton
    override fun provideType(): Class<ParentSingleEntity> = ParentSingleEntity::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = LocalQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<ParentSingleEntity>): EntityRepository<ParentSingleEntity> = repository

    @Provides @ElementsIntoSet
    fun provideTransactionalActionsIntoSet(
            action1: ParentSingleTransactionalAction1,
            action2: ParentSingleTransactionalAction2,
            action3: ParentSingleTransactionalAction3
    ): Set<AbstractTransactionalAction<*>> {
        return setOf(action1, action2, action3)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<ParentSingleEntity>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<ParentSingleEntity>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<ParentSingleEntity>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class ChildSingleEntityModule : TestRootEntityModule<ChildSingleEntity>() {

    @Provides @Singleton
    override fun provideType(): Class<ChildSingleEntity> = ChildSingleEntity::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = LocalQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<ChildSingleEntity>): EntityRepository<ChildSingleEntity> = repository

    @Provides @ElementsIntoSet
    fun provideTransactionalActionsIntoSet(
            action1: ChildSingleTransactionalAction1,
            action2: ChildSingleTransactionalAction2,
            action3: ChildSingleTransactionalAction3
    ): Set<AbstractTransactionalAction<*>> {
        return setOf(action1, action2, action3)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<ChildSingleEntity>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<ChildSingleEntity>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<ChildSingleEntity>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class SampleMultiEntityModule : TestRootEntityModule<SampleMultiEntity>() {

    @Provides @Singleton
    override fun provideType(): Class<SampleMultiEntity> = SampleMultiEntity::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = LocalQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<SampleMultiEntity>): EntityRepository<SampleMultiEntity> = repository

    @Provides @ElementsIntoSet
    fun provideTransactionalActionsIntoSet(
            action1: SampleMultiTransactionalAction1,
            action2: SampleMultiTransactionalAction2,
            action3: SampleMultiTransactionalAction3,
            action4: SampleMultiTransactionalAction4
    ): Set<AbstractTransactionalAction<*>> {
        return setOf(action1, action2, action3, action4)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<SampleMultiEntity>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<SampleMultiEntity>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<SampleMultiEntity>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class SampleManyUpdatesEntityModule : TestRootEntityModule<SampleManyUpdatesEntity>() {

    @Provides @Singleton
    override fun provideType(): Class<SampleManyUpdatesEntity> = SampleManyUpdatesEntity::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = LocalQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<SampleManyUpdatesEntity>): EntityRepository<SampleManyUpdatesEntity> = repository

    @Provides @ElementsIntoSet
    fun provideTransactionalActionsIntoSet(action1: SampleManyUpdatesTransactionalAction1): Set<AbstractTransactionalAction<*>> = setOf(action1)

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<SampleManyUpdatesEntity>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<SampleManyUpdatesEntity>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<SampleManyUpdatesEntity>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class SampleUpdateDifferentObjectsEntityModule : TestRootEntityModule<SampleUpdateDifferentObjectsEntity>() {

    @Provides @Singleton
    override fun provideType(): Class<SampleUpdateDifferentObjectsEntity> = SampleUpdateDifferentObjectsEntity::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = LocalQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<SampleUpdateDifferentObjectsEntity>): EntityRepository<SampleUpdateDifferentObjectsEntity> = repository

    @Provides @ElementsIntoSet
    fun provideTransactionalActionsIntoSet(action1: SampleUpdateDifferentObjectsTransactionalAction1): Set<AbstractTransactionalAction<*>> = setOf(action1)

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<SampleUpdateDifferentObjectsEntity>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<SampleUpdateDifferentObjectsEntity>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<SampleUpdateDifferentObjectsEntity>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class SampleManyActionsSameTypeEntityModule : TestRootEntityModule<SampleManyActionsSameTypeEntity>() {

    @Provides @Singleton
    override fun provideType(): Class<SampleManyActionsSameTypeEntity> = SampleManyActionsSameTypeEntity::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = LocalQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<SampleManyActionsSameTypeEntity>): EntityRepository<SampleManyActionsSameTypeEntity> = repository

    @Provides @ElementsIntoSet
    fun provideTransactionalActionsIntoSet(
            action1: SampleManyActionsSameTypeTransactionalAction1,
            action2: SampleManyActionsSameTypeTransactionalAction2
    ): Set<AbstractTransactionalAction<*>> {
        return setOf(action1, action2)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<SampleManyActionsSameTypeEntity>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<SampleManyActionsSameTypeEntity>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<SampleManyActionsSameTypeEntity>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class SampleUpdateWithPreviousValueEntityModule : TestRootEntityModule<SampleUpdateWithPreviousValueEntity>() {

    @Provides @Singleton
    override fun provideType(): Class<SampleUpdateWithPreviousValueEntity> = SampleUpdateWithPreviousValueEntity::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = LocalQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<SampleUpdateWithPreviousValueEntity>): EntityRepository<SampleUpdateWithPreviousValueEntity> = repository

    @Provides @ElementsIntoSet
    fun provideTransactionalActionsIntoSet(action1: SampleUpdateWithPreviousValueTransactionalAction1): Set<AbstractTransactionalAction<*>> = setOf(action1)

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<SampleUpdateWithPreviousValueEntity>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<SampleUpdateWithPreviousValueEntity>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<SampleUpdateWithPreviousValueEntity>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class SampleDefaultEntityModule : TestRootEntityModule<SampleDefaultEntity>() {

    @Provides @Singleton
    override fun provideType(): Class<SampleDefaultEntity> = SampleDefaultEntity::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = LocalQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<SampleDefaultEntity>): EntityRepository<SampleDefaultEntity> = repository

    @Provides @ElementsIntoSet
    fun provideTransactionalActionsIntoSet(action1: SampleDefaultTransactionalAction1): Set<AbstractTransactionalAction<*>> = setOf(action1)

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<SampleDefaultEntity>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<SampleDefaultEntity>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<SampleDefaultEntity>): PersistableObjectAsyncListener<*> = listener
}