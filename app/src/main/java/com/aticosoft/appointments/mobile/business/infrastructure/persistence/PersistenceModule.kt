package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.ModulePostInitializer
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListenersManager
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.configuration.PersistenceConfigurator
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.configuration.PersistenceConfiguratorModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 09/09/15.
 */
@Module(includes = arrayOf(PersistenceBaseModule::class, PersistenceConfiguratorModule::class))
/*internal*/ class PersistenceModule {

    @Singleton
    class PostInitializer @Inject protected constructor(private val entityListenersManager: EntityListenersManager) : ModulePostInitializer {

        override fun init() {
            entityListenersManager.registerListeners()
        }
    }
}

@Module
/*internal*/ class PersistenceBaseModule {

    @Provides @Singleton
    fun providePersistenceManagerFactory(persistenceConfigurator: PersistenceConfigurator) = persistenceConfigurator.createPersistenceManagerFactory()
}