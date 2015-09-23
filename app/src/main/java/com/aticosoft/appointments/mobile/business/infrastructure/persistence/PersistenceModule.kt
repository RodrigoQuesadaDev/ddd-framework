package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rodrigo on 09/09/15.
 */
@Module
/*internal*/ open class PersistenceModule {

    @Provides @Singleton
    open fun providePersistenceConfigurer(services: PersistenceConfigurer.Services) = PersistenceConfigurer(services)

    @Provides @Singleton
    fun providePersistenceManagerFactory(persistenceConfigurer: PersistenceConfigurer) = persistenceConfigurer.createPersistenceManagerFactory()
}