package com.aticosoft.appointments.mobile.business.domain.application.configuration

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServicesBase
import org.joda.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@Singleton
/*internal*/ class ConfigurationServices @Inject protected constructor() : ApplicationServicesBase() {

    class ResetConfiguration : Command()

    fun execute(command: ResetConfiguration) = command.execute {
        retrieveConfiguration().reset()
    }

    class ChangeMaxConcurrentAppointments(val value: Int) : Command()

    fun execute(command: ChangeMaxConcurrentAppointments) = command.execute {
        retrieveConfiguration().maxConcurrentAppointments = value
    }

    class ChangeTimeSlotDuration(val value: Duration) : Command()

    fun execute(command: ChangeTimeSlotDuration) = command.execute {
        retrieveConfiguration().timeSlotDuration = value
    }
}