package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.ModulePostInitializer
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidatorsManager
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.AppointmentModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client.ClientModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializersManager
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.configuration.ConfigurationModule
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.annotation.AnnotationRetention.RUNTIME

/**
 * Created by Rodrigo Quesada on 26/09/15.
 */
@Module(includes = arrayOf(ConfigurationModule::class, AppointmentModule::class, ClientModule::class))
/*internal*/ class DomainModelModule {

    @Provides @ElementsIntoSet
    fun provideEntityTypes(): Set<Class<out Entity>> = emptySet()

    @Provides @ElementsIntoSet
    fun provideValidators(): Set<EntityValidator<*, *>> = emptySet()

    @Provides @ElementsIntoSet @QueryViews
    fun provideQueryViews(): Set<Class<out Enum<*>>> = setOf()

    @Provides @ElementsIntoSet
    fun provideEntityInitializers(): Set<EntityInitializer<*>> = emptySet()

    @Singleton
    class PostInitializer @Inject protected constructor(
            private val entityValidatorsManager: EntityValidatorsManager,
            private val entityInitializersManager: EntityInitializersManager
    ) : ModulePostInitializer {

        override fun init() {
            entityValidatorsManager.registerValidators()
            entityInitializersManager.registerInitializers()
        }
    }
}

@Qualifier
@Retention(RUNTIME)
/*internal*/ annotation class QueryViews
