package com.aticosoft.appointments.mobile.business.domain.application.appointment

import com.aticosoft.appointments.mobile.business.domain.application.common.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 07/09/15.
 */
@Singleton
/*internal*/ class AppointmentServices @Inject constructor(
        context: ApplicationServices.Context,
        private val entityContext: Entity.Context,
        private val appointmentRepository: AppointmentRepository
) : ApplicationServices(context) {

    class ScheduleAppointment(val clientId: Long, val time: DateTime) : Command()

    fun execute(command: ScheduleAppointment) = command.execute {
        appointmentRepository.add(Appointment(entityContext, clientId = clientId, scheduledTime = time))
    }
}