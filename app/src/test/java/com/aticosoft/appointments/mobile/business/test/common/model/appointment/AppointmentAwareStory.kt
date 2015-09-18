package com.aticosoft.appointments.mobile.business.test.common.model.appointment

import com.aticosoft.appointments.mobile.business.domain.application.AppointmentService
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import javax.inject.Inject

/**
 * Created by rodrigo on 10/09/15.
 */
interface AppointmentAwareStory {

    var appointmentService: AppointmentService
    var appointmentRepository: AppointmentRepository
}

class AppointmentAwareStoryImpl @Inject constructor() : AppointmentAwareStory {

    @Inject lateinit override var appointmentService: AppointmentService
    @Inject lateinit override var appointmentRepository: AppointmentRepository
}