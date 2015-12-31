package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.ApplicationComponent
import com.aticosoft.appointments.mobile.business.ModulePostInitializer
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidatorsManager
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.AppointmentModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client.ClientModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializers
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializersManager
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.configuration.ConfigurationModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

/**
 * Created by Rodrigo Quesada on 26/09/15.
 */
@Module(includes = arrayOf(ConfigurationModule::class, AppointmentModule::class, ClientModule::class))
/*internal*/ open class DomainModelModule {

    val entityModuleObjects: List<EntityModule.CompanionObject> = DomainModelModule::class
            .entityModuleClasses()
            .companionObjects()

    @Provides @Singleton @EntityTypes
    open fun provideEntityTypes(): Array<Class<out Entity>> = entityModuleObjects.map { it.entityType }.toTypedArray()

    @Provides @Singleton @EntityValidators
    open fun provideValidators(): Array<EntityValidator<*>> = entityModuleObjects.flatMap { it.validators.asList() }.toTypedArray()

    @Provides @Singleton @EntityInitializers
    open fun provideEntityInitializers(entityInitializerFactory: EntityInitializer.Factory): Array<out EntityInitializer<*, *>> = with(entityInitializerFactory) {
        entityModuleObjects.map { it.createEntityInitializer(this) }.toTypedArray()
    }

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

/***************************************************************************************************
 * Public Extensions
 **************************************************************************************************/

internal inline fun <reified E : Entity> EntityInitializer.Factory.create(noinline injectCall: ApplicationComponent.(E) -> Unit) = create(E::class.java, injectCall)

/***************************************************************************************************
 * Private Code
 **************************************************************************************************/

/*internal*/ interface EntityInjection : AppointmentModule.EntityInjection, ClientModule.EntityInjection, ConfigurationModule.EntityInjection

internal annotation class EntityTypes

internal annotation class EntityValidators

/*internal*/ interface EntityModule {
    interface CompanionObject {
        val entityType: Class<out Entity>

        val validators: Array<out EntityValidator<*>>

        fun createEntityInitializer(entityInitializerFactory: EntityInitializer.Factory): EntityInitializer<*, *> = entityInitializerFactory.create()

        fun EntityInitializer.Factory.create(): EntityInitializer<*, *>
    }
}

fun KClass<*>.entityModuleClasses() = annotations.mapNotNull { it as? Module }.first().includes

//TODO It shouldn't be necessary to suppress this warning because it shouldn't even occur (as? is being used, which is safe)
@Suppress("UNCHECKED_CAST")
fun Array<KClass<*>>.companionObjects() = mapNotNull { it.nestedClasses.mapNotNull { it as? KClass<EntityModule.CompanionObject> }.first().objectInstance }
