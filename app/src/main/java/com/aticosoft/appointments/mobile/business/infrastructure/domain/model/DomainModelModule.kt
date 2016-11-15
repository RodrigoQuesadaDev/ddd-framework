package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.ModulePostInitializer
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventActionState
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.AbstractTransactionalAction
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.PersistableObjectSyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidatorsManager
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.AppointmentModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client.ClientModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.DomainModelCommonModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializersManager
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.valueobject.ValueObjectsManager
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
@Module(includes = arrayOf(DomainModelBaseModule::class, DomainModelCommonModule::class))
/*internal*/ class DomainModelModule {

    @Singleton
    class PostInitializer @Inject protected constructor(
            private val persistableObjectValidatorsManager: PersistableObjectValidatorsManager,
            private val persistableObjectInitializersManager: PersistableObjectInitializersManager,
            private val valueObjectsManager: ValueObjectsManager
    ) : ModulePostInitializer {

        override fun init() {
            persistableObjectValidatorsManager.registerValidators()
            persistableObjectInitializersManager.registerInitializers()
            valueObjectsManager.registerValueObjects()
        }
    }
}

@Module(includes = arrayOf(ConfigurationModule::class, AppointmentModule::class, ClientModule::class))
/*internal*/ class DomainModelBaseModule {

    @Provides
    fun provideEventActionStateType(): Class<EventActionState> = EventActionState::class.java

    @Provides @ElementsIntoSet
    fun provideObjectInitializers(): Set<PersistableObjectInitializer<*>> = emptySet()

    @Provides @ElementsIntoSet
    fun provideSyncListeners(): Set<PersistableObjectSyncListener<*>> = emptySet()

    @Provides @ElementsIntoSet
    fun provideAsyncListeners(): Set<PersistableObjectAsyncListener<*>> = emptySet()

    @Provides @ElementsIntoSet
    fun provideValidators(): Set<PersistableObjectValidator<*, *>> = emptySet()

    @Provides @ElementsIntoSet @QueryViews
    fun provideQueryViews(): Set<Class<out Enum<*>>> = setOf()

    @Provides @ElementsIntoSet @ValueObjects
    fun provideValueObjects(): Set<Class<*>> = emptySet()

    @Provides @ElementsIntoSet
    fun provideTransactionalActionsIntoSet(): Set<AbstractTransactionalAction<*>> = emptySet()

    @Provides @ElementsIntoSet
    fun provideEventActionsIntoSet(): Set<EventAction<*>> = emptySet()
}

@Qualifier
@Retention(RUNTIME)
/*internal*/ annotation class QueryViews

@Qualifier
@Retention(RUNTIME)
/*internal*/ annotation class ValueObjects
