package com.aticosoft.appointments.mobile.business.domain.specs.common.model.appointment

import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentObserver
import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentServices
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.TestRepository
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.common.EntityServicesAware
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.JdoAppointmentRepository
import javax.inject.Inject

/**
 * Created by rodrigo on 10/09/15.
 */
interface AppointmentServicesAware {

    val appointmentServices: AppointmentServices
    val appointmentObserver: AppointmentObserver
    val appointmentQueries: AppointmentQueries
    val appointmentRepository: TestRepository<JdoAppointmentRepository, Appointment>
}

internal class AppointmentServicesAwareImpl @Inject constructor() : EntityServicesAware<JdoAppointmentRepository, Appointment>(), AppointmentServicesAware {

    @Inject lateinit override var appointmentServices: AppointmentServices
    @Inject lateinit override var appointmentObserver: AppointmentObserver
    @Inject lateinit override var appointmentQueries: AppointmentQueries
    override val appointmentRepository by lazy { repository }
}