package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueryView
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 02/07/16.
 */
@Singleton
class AppointmentObserver @Inject protected constructor() : EntityObserver<Appointment>() {

    override val defaultQueryView = AppointmentQueryView.DEFAULT
}