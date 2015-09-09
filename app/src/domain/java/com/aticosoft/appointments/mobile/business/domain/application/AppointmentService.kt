package com.aticosoft.appointments.mobile.business.domain.application

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 07/09/15.
 */
@Singleton
class AppointmentService @Inject constructor(
        private val appointmentRepository: AppointmentRepository
) {

    fun scheduleAppointment(time: DateTime): Appointment {
        val appointment = Appointment(time)
        appointmentRepository.add(appointment)
        return appointment
    }
}