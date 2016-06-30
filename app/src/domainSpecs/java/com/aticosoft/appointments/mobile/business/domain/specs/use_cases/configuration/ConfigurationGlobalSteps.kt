package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.configuration

import com.aticosoft.appointments.mobile.business.domain.application.configuration.ConfigurationServices
import com.aticosoft.appointments.mobile.business.domain.application.configuration.ConfigurationServices.ChangeMaxConcurrentAppointments
import com.aticosoft.appointments.mobile.business.domain.application.configuration.ConfigurationServices.ChangeTimeSlotDuration
import org.jbehave.core.annotations.Given
import org.joda.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@Singleton
internal class ConfigurationGlobalSteps @Inject protected constructor(
        private val configurationServices: ConfigurationServices
) {

    @Given("\$number concurrent appointment{s|} at max")
    fun givenNumberConcurrentAppointmentsAtMax(number: Int) {
        configurationServices.execute(ChangeMaxConcurrentAppointments(number))
    }

    @Given("\$duration time slots")
    fun givenDurationTimeSlots(duration: Duration) {
        configurationServices.execute(ChangeTimeSlotDuration(duration))
    }
}