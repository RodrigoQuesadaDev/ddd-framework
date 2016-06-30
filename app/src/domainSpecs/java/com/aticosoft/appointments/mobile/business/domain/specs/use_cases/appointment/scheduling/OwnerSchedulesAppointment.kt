package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling

import com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.TimeSlotValidationException
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.UseCaseApplication
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.UseCaseApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.AppointmentGlobalSteps
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.AppointmentStory
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling.OwnerSchedulesAppointment.UnitTestApplicationImpl
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Pending
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 17/09/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class OwnerSchedulesAppointment : AppointmentStory() {

    class UnitTestApplicationImpl : UseCaseApplication<OwnerSchedulesAppointment>(UseCaseApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
        setUp { appointmentSteps.catchScheduleExceptionsOfType() }
    }

    class LocalSteps @Inject protected constructor(
            private val appointmentSteps: AppointmentGlobalSteps
    ) {
        @Then("the system throws an Exception indicating the schedule time is not allowed due to the configured maximum number of concurrent appointments")
        @Pending
        fun thenTheSystemThrowsAnExceptionIndicatingTheScheduleTimeIsNotAllowed() {
            assertThat(appointmentSteps.thrownScheduleException).isInstanceOf(TimeSlotValidationException::class.java)
        }
    }
}