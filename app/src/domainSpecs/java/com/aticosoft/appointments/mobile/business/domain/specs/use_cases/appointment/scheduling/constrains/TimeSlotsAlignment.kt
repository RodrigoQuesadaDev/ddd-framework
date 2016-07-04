package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling.constrains

import com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.timeslot.TimeSlotsAlignmentException
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.UseCaseApplication
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.UseCaseApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.AppointmentGlobalSteps
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.AppointmentStory
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling.constrains.TimeSlotsAlignment.UseCaseApplicationImpl
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@Config(application = UseCaseApplicationImpl::class)
internal class TimeSlotsAlignment : AppointmentStory() {

    class UseCaseApplicationImpl : UseCaseApplication<TimeSlotsAlignment>(UseCaseApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
        setUp { appointmentSteps.catchScheduleExceptionsOfType(TimeSlotsAlignmentException::class.java) }
    }

    class LocalSteps @Inject protected constructor(
            private val appointmentSteps: AppointmentGlobalSteps
    ) {
        @Then("the system throws an Exception indicating the schedule time doesn't align to the configured time slots")
        fun thenTheSystemThrowsAnExceptionIndicatingTheScheduleTimeDoesNotAlignToTheConfiguredTimeSlots() {
            assertThat(appointmentSteps.thrownScheduleException).isInstanceOf(TimeSlotsAlignmentException::class.java)
        }
    }
}