package com.aticosoft.appointments.mobile.business.domain.specs.use_cases

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.AppointmentGlobalSteps
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.client.ClientGlobalSteps
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.configuration.ConfigurationGlobalSteps
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 29/06/16.
 */
internal abstract class UseCaseStory : DomainStory() {

    @Inject protected lateinit var configurationSteps: ConfigurationGlobalSteps
    @Inject protected lateinit var appointmentSteps: AppointmentGlobalSteps
    @Inject protected lateinit var clientSteps: ClientGlobalSteps

    init {
        preStories("com/aticosoft/appointments/mobile/business/domain/specs/use_cases/given_stories/given_some_existing_data.story")
        steps { listOf(configurationSteps, appointmentSteps, clientSteps) }
    }
}