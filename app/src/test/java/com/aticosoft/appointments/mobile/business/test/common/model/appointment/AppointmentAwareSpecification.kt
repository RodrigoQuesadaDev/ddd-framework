package com.aticosoft.appointments.mobile.business.test.common.model.appointment

import com.aticosoft.appointments.mobile.business.domain.application.AppointmentService
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by rodrigo on 10/09/15.
 */
interface AppointmentAwareSpecification {

    var appointmentService: AppointmentService
        @Inject set
    var appointmentRepository: AppointmentRepository
        @Inject set

}

class AppointmentAwareSpecificationImpl : AppointmentAwareSpecification {

    override var appointmentService: AppointmentService by Delegates.notNull()
    override var appointmentRepository: AppointmentRepository by Delegates.notNull()
}