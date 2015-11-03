package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.ModulePostInitializer
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListenersManager
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.observation.EntityListenersModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 09/09/15.
 */
@Module(includes = arrayOf(EntityListenersModule::class))
/*internal*/ open class PersistenceModule {

    @Provides @Singleton
    open fun providePersistenceConfigurator(services: PersistenceConfigurator.Services) = PersistenceConfigurator(services)

    @Provides @Singleton
    fun providePersistenceManagerFactory(persistenceConfigurator: PersistenceConfigurator) = persistenceConfigurator.createPersistenceManagerFactory()

    @Singleton
    class PostInitializer @Inject constructor(private val entityListenersManager: EntityListenersManager) : ModulePostInitializer {

        override fun init() {
            entityListenersManager.registerListeners()
        }
    }
}