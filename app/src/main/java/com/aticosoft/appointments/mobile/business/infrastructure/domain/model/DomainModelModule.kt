package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.JdoAppointmentQueries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.JdoAppointmentRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client.JdoClientQueries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client.JdoClientRepository
import dagger.Module
import dagger.Provides
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
}

internal annotation class EntityTypes