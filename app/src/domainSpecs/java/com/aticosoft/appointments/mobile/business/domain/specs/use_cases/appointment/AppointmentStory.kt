package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.specs.use_cases.UseCaseStory
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.jbehave.core.annotations.Then
import org.joda.time.Interval
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 01/07/16.
 */
internal abstract class AppointmentStory : UseCaseStory() {

    @Inject protected lateinit var appointmentLocalSteps: LocalSteps

    init {
        steps { listOf(appointmentLocalSteps) }
    }

    @Singleton
    class LocalSteps @Inject protected constructor(
            private val appointmentObserver: EntityObserver<Appointment>,
            private val appointmentQueries: AppointmentQueries,
            private val clientObserver: EntityObserver<Client>
    ) {
        @Then("an appointment is scheduled for \$client on \$time")
        fun thenAnAppointmentIsScheduledFor(client: String, time: Interval) {
            val actualAppointments = appointmentObserver.observe(appointmentQueries.timeIs(time)).testSubscribe().firstEvent()
            assertThat(actualAppointments).haveExactly(1, ScheduledAppointment(client, time))
        }

        //region Assertions
        private inner class ScheduledAppointment(private val expectedClient: String, private val expectedTime: Interval) : Condition<Appointment>("{client: $expectedClient, time: $expectedTime}") {

            override fun matches(appointment: Appointment) = try {
                assertThat(appointment.scheduledTime).isEqualTo(expectedTime)

                val actualClient = clientObserver.observe(appointment.clientId).testSubscribe().firstEvent()
                assertThat(actualClient).isNotNull()
                assertThat(actualClient!!.name).isEqualTo(expectedClient)

                true
            }
            catch(e: AssertionError) {
                false
            }
        }
        //endregion
    }
}