package com.aticosoft.appointments.mobile.business.domain.application.appointment

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 18/10/15.
 */
@Singleton
class AppointmentObserver @Inject constructor(services: AppointmentObserver.Services) : EntityObserver<Appointment>(services, Appointment::class.java) {

    protected class Services @Inject constructor(override val entityRepository: AppointmentRepository) : EntityObserver.Services<Appointment>()
}

@Singleton
internal class AppointmentListener @Inject constructor(services: EntityListener.Services) : EntityListener<Appointment>(services, Appointment::class.java)