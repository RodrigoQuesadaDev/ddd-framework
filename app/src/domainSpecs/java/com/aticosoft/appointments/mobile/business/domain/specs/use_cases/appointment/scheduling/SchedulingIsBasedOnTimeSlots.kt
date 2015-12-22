package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling

import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentServices
import com.aticosoft.appointments.mobile.business.domain.application.client.ClientObserver
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.AppointmentSteps
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.AppointmentStory
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling.SchedulingIsBasedOnTimeSlots.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.client.ClientSteps
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.configuration.ConfigurationSteps
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.test.catchThrowable
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.joda.time.Interval
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@Config(application = TestApplicationImpl::class)
internal class SchedulingIsBasedOnTimeSlots : AppointmentStory() {

    @Inject protected lateinit var localSteps: LocalSteps
    @Inject protected lateinit var appointmentSteps: AppointmentSteps
    @Inject protected lateinit var clientSteps: ClientSteps
    @Inject protected lateinit var configurationSteps: ConfigurationSteps

    override val steps by lazy { arrayOf(localSteps, appointmentSteps, clientSteps, configurationSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<SchedulingIsBasedOnTimeSlots>

    class TestApplicationImpl() : TestApplication(DaggerSchedulingIsBasedOnTimeSlots_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject protected constructor(
            private val appointmentServices: AppointmentServices,
            private val clientObserver: ClientObserver,
            private val clientQueries: ClientQueries
    ) : ExceptionThrowingSteps {

        override var throwable: Throwable? = null

        @When("the owner schedules an appointment for \$client on \$time")
        fun whenTheOwnerSchedulesAnAppointmentFor(client: String, time: Interval) {
            val clientsResult = clientObserver.observe(clientQueries.nameLike(client)).testSubscribe().firstEvent()
            throwable = catchThrowable { appointmentServices.execute(AppointmentServices.ScheduleAppointment(clientsResult.first().id, time)) }
        }

        @Then("the appointment is successfully scheduled")
        fun thenTheAppointmentIsSuccessfullyScheduled() {
            assertThat(throwable).isNull()
        }

        @Then("the system throws an IllegalArgumentException indicating the schedule time doesn't comply with the time slot configuration")
        fun thenTheSystemThrowsAnIllegalArgumentExceptionIndicatingTheScheduleTimeDoesntComplyWithTheTimeSlotConfiguration() {
            assertThat(throwable).isInstanceOf(IllegalArgumentException::class.java)
        }
    }
}