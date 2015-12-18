package com.aticosoft.appointments.mobile.business.domain.application.appointment

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentFactory
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 07/09/15.
 */
@Singleton
/*internal*/ class AppointmentServices @Inject protected constructor(
        context: ApplicationServices.Context,
        private val factory: AppointmentFactory,
        private val repository: AppointmentRepository
) : ApplicationServices(context) {

    class ScheduleAppointment(val clientId: Long, val time: DateTime) : Command()

    fun execute(command: ScheduleAppointment) = command.execute {
        repository.add(factory.create(clientId, time))
    }
}