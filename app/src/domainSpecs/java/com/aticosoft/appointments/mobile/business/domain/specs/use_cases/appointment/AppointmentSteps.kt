package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment

import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentObserver
import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentServices
import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentServices.ScheduleAppointment
import com.aticosoft.appointments.mobile.business.domain.application.client.ClientObserver
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.testing.model.AppointmentRepositoryManager
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.joda.time.Interval
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 17/09/15.
 */
internal class AppointmentSteps @Inject constructor(
        private val appointmentRepositoryManager: AppointmentRepositoryManager,
        private val appointmentServices: AppointmentServices,
        private val appointmentObserver: AppointmentObserver,
        private val appointmentQueries: AppointmentQueries,
        private val clientObserver: ClientObserver,
        private val clientQueries: ClientQueries
) {

    @Given("no appointments scheduled")
    fun givenNoAppointmentsScheduled() {
        appointmentRepositoryManager.clear()
    }

    @When("the owner schedules an appointment for \$client on \$time")
    fun whenTheOwnerSchedulesAnAppointmentFor(client: String, time: Interval) {
        val clientsResult = clientObserver.observe(clientQueries.nameLike(client)).testSubscribe().firstEvent()
        appointmentServices.execute(ScheduleAppointment(clientsResult.first().id, time))
    }

    @Then("an appointment is scheduled for \$client on \$time")
    fun thenAnAppointmentIsScheduledFor(client: String, time: Interval) {

        val actualAppointments = appointmentObserver.observe(appointmentQueries.timeIs(time)).testSubscribe().firstEvent()

        assertThat(actualAppointments).haveExactly(1, ScheduledAppointment(client, time))
    }

    private inner class ScheduledAppointment(private val client: String, private val time: Interval) : Condition<Appointment>("{client: $client, time: $time}") {

        override fun matches(appointment: Appointment) = try {
            assertThat(appointment.scheduledTime).isEqualTo(time)

            val actualClient = clientObserver.observe(appointment.clientId).testSubscribe().firstEvent()
            assertThat(actualClient).isNotNull()
            assertThat(actualClient!!.name).isEqualTo(client)
            true
        }
        catch(e: AssertionError) {
            false
        }
    }
}