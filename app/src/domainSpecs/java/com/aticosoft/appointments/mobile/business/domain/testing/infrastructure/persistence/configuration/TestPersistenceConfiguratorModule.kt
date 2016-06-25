package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.configuration

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.configuration.PersistenceConfigurator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Module
internal class TestPersistenceConfiguratorModule {

    @Provides @Singleton
    fun providePersistenceConfigurator(configurator: TestPersistenceConfiguratorImpl): PersistenceConfigurator = configurator
}