package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.test_data

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
internal class NoSubscriptionsEntityModule : TestRootEntityModule<NoSubscriptionsEntity>() {

    @Provides @Singleton
    override fun provideType(): Class<NoSubscriptionsEntity> = NoSubscriptionsEntity::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = LocalQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<NoSubscriptionsEntity>): EntityRepository<NoSubscriptionsEntity> = repository

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<NoSubscriptionsEntity>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<NoSubscriptionsEntity>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<NoSubscriptionsEntity>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class OneSubscriptionEntityModule : TestRootEntityModule<OneSubscriptionEntity>() {

    @Provides @Singleton
    override fun provideType(): Class<OneSubscriptionEntity> = OneSubscriptionEntity::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = LocalQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<OneSubscriptionEntity>): EntityRepository<OneSubscriptionEntity> = repository

    @Provides @ElementsIntoSet
    fun provideTransactionalActionsIntoSet(action1: OneSubscriptionTransactionalAction1): Set<AbstractTransactionalAction<*>> = setOf(action1)

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<OneSubscriptionEntity>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<OneSubscriptionEntity>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<OneSubscriptionEntity>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class FiveSubscriptionsEntityModule : TestRootEntityModule<FiveSubscriptionsEntity>() {

    @Provides @Singleton
    override fun provideType(): Class<FiveSubscriptionsEntity> = FiveSubscriptionsEntity::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = LocalQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<FiveSubscriptionsEntity>): EntityRepository<FiveSubscriptionsEntity> = repository

    @Provides @ElementsIntoSet
    fun provideTransactionalActionsIntoSet(
            action1: FiveSubscriptionsTransactionalAction1,
            action2: FiveSubscriptionsTransactionalAction2,
            action3: FiveSubscriptionsTransactionalAction3,
            action4: FiveSubscriptionsTransactionalAction4,
            action5: FiveSubscriptionsTransactionalAction5
    ): Set<AbstractTransactionalAction<*>> {
        return setOf(action1, action2, action3, action4, action5)
    }

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<FiveSubscriptionsEntity>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<FiveSubscriptionsEntity>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<FiveSubscriptionsEntity>): PersistableObjectAsyncListener<*> = listener
}