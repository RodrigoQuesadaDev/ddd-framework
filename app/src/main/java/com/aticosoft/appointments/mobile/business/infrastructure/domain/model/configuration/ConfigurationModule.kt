package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.domain.model.configuration.Configuration
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationQueries
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.EntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.create
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
@Module
/*internal*/ class ConfigurationModule : EntityModule {
    companion object : EntityModule.CompanionObject {

        override val entityType = Configuration::class.java

        override val validators = emptyArray<EntityValidator<*>>()

        override fun EntityInitializer.Factory.create() = create<Configuration> { inject(it) }
    }

    @Provides @Singleton
    fun provideConfigurationQueries(configurationQueries: JdoConfigurationQueries): ConfigurationQueries = configurationQueries

    @Provides @Singleton
    fun provideConfigurationRepository(configurationRepository: JdoConfigurationRepository): ConfigurationRepository = configurationRepository

    interface EntityInjection {
        fun inject(entity: Configuration)
    }
}