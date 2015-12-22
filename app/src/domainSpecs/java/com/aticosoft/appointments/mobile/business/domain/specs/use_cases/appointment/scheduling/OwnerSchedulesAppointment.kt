package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling

import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentObserver
import com.aticosoft.appointments.mobile.business.domain.application.client.ClientObserver
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.AppointmentSteps
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.AppointmentStory
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment.scheduling.OwnerSchedulesAppointment.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.client.ClientSteps
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.configuration.ConfigurationSteps
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.jbehave.core.annotations.Then
import org.joda.time.Interval
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/09/15.
 */
@Config(application = TestApplicationImpl::class)
internal class OwnerSchedulesAppointment : AppointmentStory() {

    @Inject protected lateinit var localSteps: LocalSteps
    @Inject protected lateinit var appointmentSteps: AppointmentSteps
    @Inject protected lateinit var clientSteps: ClientSteps
    @Inject protected lateinit var configurationSteps: ConfigurationSteps

    override val steps by lazy { arrayOf(localSteps, appointmentSteps, clientSteps, configurationSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<OwnerSchedulesAppointment>

    class TestApplicationImpl() : TestApplication(DaggerOwnerSchedulesAppointment_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject protected constructor(
            private val appointmentObserver: AppointmentObserver,
            private val appointmentQueries: AppointmentQueries,
            private val clientObserver: ClientObserver
    ) {
        @Then("an appointment is scheduled for \$client on \$time")
        fun thenAnAppointmentIsScheduledFor(client: String, time: Interval) {

            val actualAppointments = appointmentObserver.observe(appointmentQueries.timeIs(time)).testSubscribe().firstEvent()

            assertThat(actualAppointments).haveExactly(1, ScheduledAppointment(client, time, this))
        }

        //TODO make inner and remove parent after KT-9328 is fixed
        private class ScheduledAppointment(private val client: String, private val time: Interval, private val parent: LocalSteps) : Condition<Appointment>("{client: $client, time: $time}") {

            override fun matches(appointment: Appointment) = try {
                assertThat(appointment.scheduledTime).isEqualTo(time)

                val actualClient = parent.clientObserver.observe(appointment.clientId).testSubscribe().firstEvent()
                assertThat(actualClient).isNotNull()
                assertThat(actualClient!!.name).isEqualTo(client)
                true
            }
            catch(e: AssertionError) {
                false
            }
        }
    }
}