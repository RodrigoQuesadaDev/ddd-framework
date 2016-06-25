package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
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
internal class OddValueAndEmailParentModule : TestRootEntityModule<OddValueAndEmailParent,
        OddValueAndEmailParentQueries, OddValueAndEmailParentQueries,
        OddValueAndEmailParentQueryView,
        OddValueAndEmailParentRepository,
        EntityValidator<OddValueAndEmailParent, *>,
        EntityInitializer<OddValueAndEmailParent>,
        EntityListener<OddValueAndEmailParent>>() {

    @Provides @Singleton
    override fun provideEntityType(): Class<OddValueAndEmailParent> = OddValueAndEmailParent::class.java

    @Provides @IntoSet
    override fun provideEntityTypeIntoSet(): Class<out Entity> = provideEntityType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = OddValueAndEmailParentQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: OddValueAndEmailParentRepository): Repository<OddValueAndEmailParent> = repository

    @Provides @IntoSet
    override fun provideEntityInitializerIntoSet(initializer: EntityInitializer<OddValueAndEmailParent>): EntityInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideEntityListenerIntoSet(listener: EntityListener<OddValueAndEmailParent>): EntityListener<*> = listener
}

@Module
internal class OddValueAndEmailChildModule : EntityModule<OddValueAndEmailChild,
        EntityValidator<OddValueAndEmailChild, *>,
        EntityInitializer<OddValueAndEmailChild>,
        EntityListener<OddValueAndEmailChild>> {

    @Provides @Singleton
    override fun provideEntityType(): Class<OddValueAndEmailChild> = OddValueAndEmailChild::class.java

    @Provides @IntoSet
    override fun provideEntityTypeIntoSet(): Class<out Entity> = provideEntityType()

    @Provides @IntoSet
    override fun provideEntityInitializerIntoSet(initializer: EntityInitializer<OddValueAndEmailChild>): EntityInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideEntityListenerIntoSet(listener: EntityListener<OddValueAndEmailChild>): EntityListener<*> = listener
}

@Module
internal class PrimeNumberAndGmailParentModule : TestRootEntityModule<PrimeNumberAndGmailParent,
        PrimeNumberAndGmailParentQueries, PrimeNumberAndGmailParentQueries,
        PrimeNumberAndGmailParentQueryView,
        PrimeNumberAndGmailParentRepository,
        PrimeNumberAndGmailParentValidator,
        EntityInitializer<PrimeNumberAndGmailParent>,
        EntityListener<PrimeNumberAndGmailParent>>() {

    @Provides @Singleton
    override fun provideEntityType(): Class<PrimeNumberAndGmailParent> = PrimeNumberAndGmailParent::class.java

    @Provides @IntoSet
    override fun provideEntityTypeIntoSet(): Class<out Entity> = provideEntityType()

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = PrimeNumberAndGmailParentQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: PrimeNumberAndGmailParentRepository): Repository<PrimeNumberAndGmailParent> = repository

    @Provides @IntoSet
    fun provideValidators(): EntityValidator<*, *> = PrimeNumberAndGmailParentValidator()

    @Provides @IntoSet
    override fun provideEntityInitializerIntoSet(initializer: EntityInitializer<PrimeNumberAndGmailParent>): EntityInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideEntityListenerIntoSet(listener: EntityListener<PrimeNumberAndGmailParent>): EntityListener<*> = listener
}

@Module
internal class PrimeNumberAndGmailChildModule : EntityModule<PrimeNumberAndGmailChild,
        PrimeNumberAndGmailChildValidator,
        EntityInitializer<PrimeNumberAndGmailChild>,
        EntityListener<PrimeNumberAndGmailChild>> {

    @Provides @Singleton
    override fun provideEntityType(): Class<PrimeNumberAndGmailChild> = PrimeNumberAndGmailChild::class.java

    @Provides @IntoSet
    override fun provideEntityTypeIntoSet(): Class<out Entity> = provideEntityType()

    @Provides @IntoSet
    fun provideValidators(): EntityValidator<*, *> = PrimeNumberAndGmailChildValidator()

    @Provides @IntoSet
    override fun provideEntityInitializerIntoSet(initializer: EntityInitializer<PrimeNumberAndGmailChild>): EntityInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideEntityListenerIntoSet(listener: EntityListener<PrimeNumberAndGmailChild>): EntityListener<*> = listener
}