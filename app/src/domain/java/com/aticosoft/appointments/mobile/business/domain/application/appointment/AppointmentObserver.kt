package com.aticosoft.appointments.mobile.business.domain.application.appointment

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 18/10/15.
 */
@Singleton
class AppointmentObserver @Inject constructor(services: EntityObserver.Services, repository: AppointmentRepository) : EntityObserver<Appointment>(services, repository, Appointment::class)