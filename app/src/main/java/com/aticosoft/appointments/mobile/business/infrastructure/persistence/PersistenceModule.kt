package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.ModulePostInitializer
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 09/09/15.
 */
@Module
/*internal*/ open class PersistenceModule {

    @Provides @Singleton
    open fun providePersistenceConfigurer(services: PersistenceConfigurator.Services) = PersistenceConfigurator(services)

    @Provides @Singleton
    fun providePersistenceManagerFactory(persistenceConfigurer: PersistenceConfigurator) = persistenceConfigurer.createPersistenceManagerFactory()

    @Singleton
    class PostInitializer @Inject constructor(private val entityObserversManager: EntityObserversManager) : ModulePostInitializer {

        override fun init() {
            entityObserversManager.registerObservers()
        }
    }
}