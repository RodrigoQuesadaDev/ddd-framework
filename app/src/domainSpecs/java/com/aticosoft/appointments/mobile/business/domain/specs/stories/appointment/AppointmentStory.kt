package com.aticosoft.appointments.mobile.business.domain.specs.stories.appointment

import com.aticosoft.appointments.mobile.business.domain.specs.stories.DomainStory
import com.aticosoft.appointments.mobile.business.domain.specs.stories.client.ClientSteps
import org.jbehave.core.steps.InstanceStepsFactory
import org.jbehave.core.steps.ParameterConverters

/**
 * Created by rodrigo on 17/09/15.
 */
internal abstract class AppointmentStory : DomainStory() {

    override fun stepsFactory() = InstanceStepsFactory(configuration(),
            AppointmentSteps(this),
            ClientSteps(this)
    )

    override fun customConverters(): Array<ParameterConverters.ParameterConverter> = arrayOf(
            AppointmentTimeConverter()
    )
}