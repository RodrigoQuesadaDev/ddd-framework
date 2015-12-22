package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import org.jbehave.core.steps.ParameterConverters.ParameterConverter
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 17/09/15.
 */
internal abstract class AppointmentStory : DomainStory() {

    @Inject protected lateinit var appointmentTimeConverter: AppointmentTimeConverter

    override val storyConverters by lazy { arrayOf<ParameterConverter>(appointmentTimeConverter) }
}