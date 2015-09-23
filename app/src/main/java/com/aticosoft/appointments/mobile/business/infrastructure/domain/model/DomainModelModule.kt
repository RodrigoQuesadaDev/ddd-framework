package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.JdoAppointmentRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rodrigo on 26/09/15.
 */
@Module
internal class DomainModelModule {

    @Provides @Singleton
    fun provideAppointmentRepository(appointmentRepository: JdoAppointmentRepository): AppointmentRepository = appointmentRepository

    @Provides @Singleton
    fun provideClientRepository(clientRepository: JdoClientRepository): ClientRepository = clientRepository
}