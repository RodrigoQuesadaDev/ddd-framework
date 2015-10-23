package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.ModulePostInitializer
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.observer.EntityObserversManager
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.observer.EntityObserversModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 09/09/15.
 */
@Module(includes = arrayOf(EntityObserversModule::class))
/*internal*/ open class PersistenceModule {

    @Provides @Singleton
    open fun providePersistenceConfigurator(services: PersistenceConfigurator.Services) = PersistenceConfigurator(services)

    @Provides @Singleton
    fun providePersistenceManagerFactory(persistenceConfigurator: PersistenceConfigurator) = persistenceConfigurator.createPersistenceManagerFactory()

    @Singleton
    class PostInitializer @Inject constructor(private val entityObserversManager: EntityObserversManager) : ModulePostInitializer {

        override fun init() {
            entityObserversManager.registerObservers()
        }
    }
}