package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.configuration

import com.aticosoft.appointments.mobile.business.domain.application.configuration.ConfigurationServices
import com.aticosoft.appointments.mobile.business.domain.application.configuration.ConfigurationServices.*
import org.jbehave.core.annotations.Given
import org.joda.time.Duration
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
internal class ConfigurationSteps @Inject protected constructor(
        private val configurationServices: ConfigurationServices
) {

    @Given("a default configuration")
    fun givenADefaultConfiguration() = configurationServices.execute(ResetConfiguration())

    @Given("a maximum number of concurrent appointments equal to \$value")
    fun givenAMaximumNumberOfConcurrentAppointments(value: Int) {
        configurationServices.execute(ChangeMaxConcurrentAppointments(value))
    }

    @Given("a configured time slot of \$duration")
    fun givenAConfiguredTimeSlotOf15Minutes(duration: Duration) {
        configurationServices.execute(ChangeTimeSlotDuration(duration))
    }
}