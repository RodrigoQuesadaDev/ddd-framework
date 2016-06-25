package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.client.validation.ConfigurationValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.configuration.Configuration
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationQueries
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationQueryView
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.QueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.RootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
@Module
/*internal*/ class ConfigurationModule : RootEntityModule<Configuration,
        ConfigurationQueries, JdoConfigurationQueries,
        ConfigurationQueryView,
        JdoConfigurationRepository,
        ConfigurationValidator<*>,
        EntityInitializer<Configuration>,
        EntityListener<Configuration>> {

    @Provides
    override fun provideEntityType(): Class<Configuration> = Configuration::class.java

    @Provides @IntoSet
    override fun provideEntityTypeIntoSet(): Class<out Entity> = provideEntityType()

    @Provides @Singleton
    override fun provideQueries(queries: JdoConfigurationQueries): ConfigurationQueries = queries

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = ConfigurationQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoConfigurationRepository): Repository<Configuration> = repository

    @Provides @IntoSet
    override fun provideEntityInitializerIntoSet(initializer: EntityInitializer<Configuration>): EntityInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideEntityListenerIntoSet(listener: EntityListener<Configuration>): EntityListener<*> = listener
}