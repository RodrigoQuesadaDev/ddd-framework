package com.aticosoft.appointments.mobile.business.infrastructure.persistence;

import javax.inject.Singleton;
import javax.jdo.PersistenceManagerFactory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rodrigo on 02/08/15.
 */
@Module
public class PersistenceModule {

    @Provides @Singleton
    protected PersistenceManagerFactory providePersistenceManagerFactory(PersistenceConfigurer persistenceConfigurer) {
        return persistenceConfigurer.createPersistenceManagerFactory();
    }
}
