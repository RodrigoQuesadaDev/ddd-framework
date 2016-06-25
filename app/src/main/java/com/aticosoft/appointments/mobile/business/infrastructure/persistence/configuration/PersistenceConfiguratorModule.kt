package com.aticosoft.appointments.mobile.business.infrastructure.persistence.configuration

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Module
internal class PersistenceConfiguratorModule {

    @Provides @Singleton
    fun providePersistenceConfigurator(configurator: PersistenceConfiguratorImpl): PersistenceConfigurator = configurator
}