package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment

import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentObserver
import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentServices
import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentServices.ScheduleAppointment
import com.aticosoft.appointments.mobile.business.domain.application.client.ClientObserver
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.testing.model.AppointmentRepositoryManager
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.joda.time.DateTime
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

    @When("the owner schedules an appointment for \$client on \$date")
    fun whenTheOwnerSchedulesAnAppointmentFor(client: String, date: DateTime) {
        val clientsResult = clientObserver.observe(clientQueries.nameLike(client)).testSubscribe().firstEvent()
        appointmentServices.execute(ScheduleAppointment(clientsResult.first().id, date))
    }

    @Then("an appointment is scheduled for \$client on \$date")
    fun thenAnAppointmentIsScheduledFor(client: String, date: DateTime) {

        val actualAppointment = appointmentObserver.observe(appointmentQueries.dateIs(date)).testSubscribe().firstEvent()
        assertThat(actualAppointment).isNotNull()
        assertThat(actualAppointment!!.scheduledTime).isEqualTo(date)

        val actualClient = clientObserver.observe(actualAppointment.clientId).testSubscribe().firstEvent()
        assertThat(actualClient).isNotNull()
        assertThat(actualClient!!.name).isEqualTo(client)
    }
}