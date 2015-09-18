package com.aticosoft.appointments.mobile.business.domain.specs.appointment

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import org.jbehave.core.steps.InstanceStepsFactory

/**
 * Created by rodrigo on 17/09/15.
 */
abstract class AppointmentStory : DomainStory() {

    override fun candidateSteps() = InstanceStepsFactory(configuration(), AppointmentSteps(this)).createCandidateSteps()
}