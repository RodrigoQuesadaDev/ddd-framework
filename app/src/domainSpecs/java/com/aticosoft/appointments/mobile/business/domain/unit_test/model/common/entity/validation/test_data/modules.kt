package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.PersistableObjectSyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
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
internal class OddValueAndEmailParentModule : TestRootEntityModule<OddValueAndEmailParent>() {

    @Provides @Singleton
    override fun provideType(): Class<OddValueAndEmailParent> = OddValueAndEmailParent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = OddValueAndEmailParentQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<OddValueAndEmailParent>): EntityRepository<OddValueAndEmailParent> = repository

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<OddValueAndEmailParent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<OddValueAndEmailParent>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<OddValueAndEmailParent>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class OddValueAndEmailChildModule : EntityModule<OddValueAndEmailChild> {

    @Provides @Singleton
    override fun provideType(): Class<OddValueAndEmailChild> = OddValueAndEmailChild::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<OddValueAndEmailChild>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<OddValueAndEmailChild>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<OddValueAndEmailChild>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class PrimeNumberAndGmailParentModule : TestRootEntityModule<PrimeNumberAndGmailParent>() {

    @Provides @Singleton
    override fun provideType(): Class<PrimeNumberAndGmailParent> = PrimeNumberAndGmailParent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = PrimeNumberAndGmailParentQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<PrimeNumberAndGmailParent>): EntityRepository<PrimeNumberAndGmailParent> = repository

    @Provides @IntoSet
    fun provideValidators(primeNumberAndGmailParentValidator: PrimeNumberAndGmailParentValidator): PersistableObjectValidator<*, *> = primeNumberAndGmailParentValidator

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<PrimeNumberAndGmailParent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<PrimeNumberAndGmailParent>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<PrimeNumberAndGmailParent>): PersistableObjectAsyncListener<*> = listener
}

@Module
internal class PrimeNumberAndGmailChildModule : EntityModule<PrimeNumberAndGmailChild> {

    @Provides @Singleton
    override fun provideType(): Class<PrimeNumberAndGmailChild> = PrimeNumberAndGmailChild::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out Entity> = provideType()

    @Provides @IntoSet
    fun provideValidators(primeNumberAndGmailChildValidator: PrimeNumberAndGmailChildValidator): PersistableObjectValidator<*, *> = primeNumberAndGmailChildValidator

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<PrimeNumberAndGmailChild>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideSyncListenerIntoSet(listener: PersistableObjectSyncListener<PrimeNumberAndGmailChild>): PersistableObjectSyncListener<*> = listener

    @Provides @IntoSet
    override fun provideAsyncListenerIntoSet(listener: PersistableObjectAsyncListener<PrimeNumberAndGmailChild>): PersistableObjectAsyncListener<*> = listener
}