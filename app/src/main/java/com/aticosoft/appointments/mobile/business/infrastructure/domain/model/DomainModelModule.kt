package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.ApplicationComponent
import com.aticosoft.appointments.mobile.business.ModulePostInitializer
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.configuration.Configuration
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationQueries
import com.aticosoft.appointments.mobile.business.domain.model.configuration.ConfigurationRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.JdoAppointmentQueries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.JdoAppointmentRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client.JdoClientQueries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client.JdoClientRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInjector
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInjectors
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInjectorsManager
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.configuration.JdoConfigurationQueries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.configuration.JdoConfigurationRepository
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 26/09/15.
 */
@Module
/*internal*/ open class DomainModelModule {

    @Provides @Singleton @EntityTypes
    open fun provideEntityTypes(): Array<Class<out Entity>> = arrayOf(Appointment::class.java, Client::class.java)

    @Provides @Singleton
    fun provideAppointmentQueries(appointmentQueries: JdoAppointmentQueries): AppointmentQueries = appointmentQueries

    @Provides @Singleton
    fun provideAppointmentRepository(appointmentRepository: JdoAppointmentRepository): AppointmentRepository = appointmentRepository

    @Provides @Singleton
    fun provideClientRepository(clientRepository: JdoClientRepository): ClientRepository = clientRepository

    @Provides @Singleton
    fun provideClientQueries(clientQueries: JdoClientQueries): ClientQueries = clientQueries

    @Provides @Singleton
    fun provideConfigurationRepository(configurationRepository: JdoConfigurationRepository): ConfigurationRepository = configurationRepository

    @Provides @Singleton
    fun provideConfigurationQueries(configurationQueries: JdoConfigurationQueries): ConfigurationQueries = configurationQueries

    @Provides @Singleton @EntityInjectors
    open fun provideEntityInjectors(entityInjectorFactory: EntityInjector.Factory): Array<out EntityInjector<*, *>> = with(entityInjectorFactory) {
        arrayOf(
                create<Appointment> { inject(it) },
                create<Client> { inject(it) },
                create<Configuration> { inject(it) }
        )
    }

    @Singleton
    class PostInitializer @Inject protected constructor(private val entityInjectorsManager: EntityInjectorsManager) : ModulePostInitializer {

        override fun init() = entityInjectorsManager.registerInjectors()
    }
}

/*internal*/ interface EntityInjection {

    fun inject(entity: Appointment)
    fun inject(entity: Client)
    fun inject(entity: Configuration)
}

internal annotation class EntityTypes

/***************************************************************************************************
 * Extensions
 **************************************************************************************************/

private inline fun <reified E : Entity> EntityInjector.Factory.create(noinline injectCall: ApplicationComponent.(E) -> Unit) = create(E::class.java, injectCall)