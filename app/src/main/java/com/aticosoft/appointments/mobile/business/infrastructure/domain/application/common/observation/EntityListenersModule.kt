package com.aticosoft.appointments.mobile.business.infrastructure.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 27/10/15.
 */
@Module
/*internal*/ class EntityListenersModule {

    //TODO change this (use multibinding)
    //TODO change servicesPROVIDER stuff?? (is it really needed???)
    /*@Provides @Singleton
    fun provideEntityListeners(entityTypes: MutableSet<Class<out Entity>>, servicesProvider: Provider<EntityListener.Services>): Array<EntityListener<*>> {
        return entityTypes.map { EntityListener(servicesProvider.get(), it) }.toTypedArray()
    }*/
}
