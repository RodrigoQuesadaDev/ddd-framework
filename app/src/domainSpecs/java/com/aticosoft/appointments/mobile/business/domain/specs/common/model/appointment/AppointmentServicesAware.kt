package com.aticosoft.appointments.mobile.business.domain.specs.common.model.appointment

import com.aticosoft.appointments.mobile.business.domain.application.AppointmentServices
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.TestRepositoryWrapper
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.common.EntityServicesAware
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.JdoAppointmentRepository
import javax.inject.Inject

/**
 * Created by rodrigo on 10/09/15.
 */
interface AppointmentServicesAware {

    val appointmentServices: AppointmentServices
    val appointmentRepository: TestRepositoryWrapper<JdoAppointmentRepository, Appointment, AppointmentQueries>
}

internal class AppointmentServicesAwareImpl @Inject constructor() : EntityServicesAware<JdoAppointmentRepository, Appointment, AppointmentQueries>(), AppointmentServicesAware {

    @Inject lateinit override var appointmentServices: AppointmentServices
    override val appointmentRepository by lazy { repository }
}