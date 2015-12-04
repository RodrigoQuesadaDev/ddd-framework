package com.aticosoft.appointments.mobile.business.infrastructure.persistence.observation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.EntityTypes
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 27/10/15.
 */
@Module
/*internal*/ class EntityListenersModule {

    @Provides @Singleton @EntityListeners
    fun provideEntityListeners(@EntityTypes entityTypes: Array<Class<out Entity>>, servicesProvider: Provider<EntityListener.Services>): Array<EntityListener<*>> {
        return entityTypes.map { EntityListener(servicesProvider.get(), it) }.toTypedArray()
    }
}

internal annotation class EntityListeners