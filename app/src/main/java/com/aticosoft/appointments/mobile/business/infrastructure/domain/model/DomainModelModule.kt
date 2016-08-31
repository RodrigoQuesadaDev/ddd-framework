package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.ModulePostInitializer
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidatorsManager
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.AppointmentModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client.ClientModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializersManager
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
    fun provideObjectTypes(): Set<Class<out PersistableObject<*>>> = emptySet()

    @Provides @ElementsIntoSet
    fun provideObjectInitializers(): Set<PersistableObjectInitializer<*>> = emptySet()

    @Provides @ElementsIntoSet
    fun provideValidators(): Set<PersistableObjectValidator<*, *>> = emptySet()

    @Provides @ElementsIntoSet @QueryViews
    fun provideQueryViews(): Set<Class<out Enum<*>>> = setOf()

    @Singleton
    class PostInitializer @Inject protected constructor(
            private val persistableObjectValidatorsManager: PersistableObjectValidatorsManager,
            private val persistableObjectInitializersManager: PersistableObjectInitializersManager
    ) : ModulePostInitializer {

        override fun init() {
            persistableObjectValidatorsManager.registerValidators()
            persistableObjectInitializersManager.registerInitializers()
        }
    }
}

@Qualifier
@Retention(RUNTIME)
/*internal*/ annotation class QueryViews
