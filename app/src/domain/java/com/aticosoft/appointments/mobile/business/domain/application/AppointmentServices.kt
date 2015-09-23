package com.aticosoft.appointments.mobile.business.domain.application

import com.aticosoft.appointments.mobile.business.domain.application.AppointmentServices.ScheduleAppointment
import com.aticosoft.appointments.mobile.business.domain.model.IdentityGenerator
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 07/09/15.
 */
@Singleton
/*internal*/ class AppointmentServices @Inject constructor(
        val scheduleAppointment: ScheduleAppointment
) {

    @Singleton
    class ScheduleAppointment @Inject constructor(
            services: ApplicationService.Services,
            private val identityGenerator: IdentityGenerator,
            private val appointmentRepository: AppointmentRepository
    ) : ApplicationService<ScheduleAppointment.Command>(services) {

        class Command(val clientId: Long, val time: DateTime) : ApplicationCommand

        override protected fun doExecute(command: Command) {
            with(command) {
                appointmentRepository.add(Appointment(
                        identityGenerator.generate(), clientId = clientId, scheduledTime = time)
                )
            }
        }
    }
}