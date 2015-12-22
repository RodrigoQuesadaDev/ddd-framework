package com.aticosoft.appointments.mobile.business.domain.application.appointment

import com.aticosoft.appointments.mobile.business.domain.application.appointment.checking.checkIntervalMatchesTimeSlots
import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServicesBase
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentFactory
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
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
        private val repository: AppointmentRepository
) : ApplicationServicesBase(context) {

    class ScheduleAppointment(val clientId: Long, val time: Interval) : Command()

    fun execute(command: ScheduleAppointment) = command.execute {
        checkIntervalMatchesTimeSlots(time, retrieveConfiguration())
        repository.add(factory.create(clientId, time))
    }
}