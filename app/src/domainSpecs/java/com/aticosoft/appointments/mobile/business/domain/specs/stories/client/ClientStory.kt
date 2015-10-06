package com.aticosoft.appointments.mobile.business.domain.specs.stories.client

import com.aticosoft.appointments.mobile.business.domain.specs.stories.DomainStory
import org.jbehave.core.steps.InstanceStepsFactory

/**
 * Created by rodrigo on 05/10/15.
 */
internal abstract class ClientStory : DomainStory() {

    override fun stepsFactory() = InstanceStepsFactory(configuration(), ClientSteps(this))
}