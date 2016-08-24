package com.aticosoft.appointments.mobile.business.domain.application.appointment

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServicesBase
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentFactory
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import org.joda.time.Interval
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 07/09/15.
 */
@Singleton
/*internal*/ class AppointmentServices @Inject protected constructor(
        context: ApplicationServicesBase.Context,
        private val factory: AppointmentFactory,
        private val repository: EntityRepository<Appointment>
) : ApplicationServicesBase(context) {

    class ScheduleAppointment(val clientId: String, val time: Interval) : Command()

    fun execute(command: ScheduleAppointment) = command.execute {
        repository.add(factory.create(clientId, time))
    }
}