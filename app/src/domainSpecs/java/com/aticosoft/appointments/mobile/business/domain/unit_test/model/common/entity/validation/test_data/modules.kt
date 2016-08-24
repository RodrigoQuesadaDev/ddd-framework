package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityListener
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
internal class OddValueAndEmailParentModule : TestRootEntityModule<OddValueAndEmailParent,
        OddValueAndEmailParentQueries, OddValueAndEmailParentQueries,
        OddValueAndEmailParentQueryView,
        OddValueAndEmailParentRepository,
        PersistableObjectValidator<OddValueAndEmailParent, *>,
        PersistableObjectInitializer<OddValueAndEmailParent>,
        EntityListener<OddValueAndEmailParent>>() {

    @Provides @Singleton
    override fun provideType(): Class<OddValueAndEmailParent> = OddValueAndEmailParent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = OddValueAndEmailParentQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: OddValueAndEmailParentRepository): EntityRepository<OddValueAndEmailParent> = repository

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<OddValueAndEmailParent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityListener<OddValueAndEmailParent>): PersistableObjectListener<*, *> = listener
}

@Module
internal class OddValueAndEmailChildModule : EntityModule<OddValueAndEmailChild,
        PersistableObjectValidator<OddValueAndEmailChild, *>,
        PersistableObjectInitializer<OddValueAndEmailChild>,
        EntityListener<OddValueAndEmailChild>> {

    @Provides @Singleton
    override fun provideType(): Class<OddValueAndEmailChild> = OddValueAndEmailChild::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<OddValueAndEmailChild>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityListener<OddValueAndEmailChild>): PersistableObjectListener<*, *> = listener
}

@Module
internal class PrimeNumberAndGmailParentModule : TestRootEntityModule<PrimeNumberAndGmailParent,
        PrimeNumberAndGmailParentQueries, PrimeNumberAndGmailParentQueries,
        PrimeNumberAndGmailParentQueryView,
        PrimeNumberAndGmailParentRepository,
        PrimeNumberAndGmailParentValidator,
        PersistableObjectInitializer<PrimeNumberAndGmailParent>,
        EntityListener<PrimeNumberAndGmailParent>>() {

    @Provides @Singleton
    override fun provideType(): Class<PrimeNumberAndGmailParent> = PrimeNumberAndGmailParent::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = PrimeNumberAndGmailParentQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: PrimeNumberAndGmailParentRepository): EntityRepository<PrimeNumberAndGmailParent> = repository

    @Provides @IntoSet
    fun provideValidators(): PersistableObjectValidator<*, *> = PrimeNumberAndGmailParentValidator()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<PrimeNumberAndGmailParent>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityListener<PrimeNumberAndGmailParent>): PersistableObjectListener<*, *> = listener
}

@Module
internal class PrimeNumberAndGmailChildModule : EntityModule<PrimeNumberAndGmailChild,
        PrimeNumberAndGmailChildValidator,
        PersistableObjectInitializer<PrimeNumberAndGmailChild>,
        EntityListener<PrimeNumberAndGmailChild>> {

    @Provides @Singleton
    override fun provideType(): Class<PrimeNumberAndGmailChild> = PrimeNumberAndGmailChild::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @IntoSet
    fun provideValidators(): PersistableObjectValidator<*, *> = PrimeNumberAndGmailChildValidator()

    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<PrimeNumberAndGmailChild>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityListener<PrimeNumberAndGmailChild>): PersistableObjectListener<*, *> = listener
}