package com.aticosoft.appointments.mobile.business.domain.specs.stories.appointment

import com.aticosoft.appointments.mobile.business.domain.application.AppointmentServices.ScheduleAppointment
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.appointment.AppointmentServicesAware
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.client.ClientServicesAware
import com.aticosoft.appointments.mobile.business.domain.specs.stories.DomainStory
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.joda.time.DateTime

/**
 * Created by rodrigo on 17/09/15.
 */
internal class AppointmentSteps(domainStory: DomainStory) : AppointmentServicesAware by domainStory, ClientServicesAware by domainStory {

    @Given("no appointments scheduled")
    fun noAppointmentsScheduled() {
        appointmentRepository.clear()
    }

    @When("the owner schedules an appointment for \$client on \$date")
    fun theOwnerSchedulesAnAppointmentFor(client: String, date: DateTime) {
        val clientsResult = clientRepository.find(clientRepository.QUERIES.nameLike(client))
        appointmentServices.scheduleAppointment.execute(ScheduleAppointment.Command(clientsResult.first().id, date))
    }

    @Then("an appointment is scheduled for \$client on \$date")
    fun anAppointmentIsScheduledFor(client: String, date: DateTime) {

        val actualAppointment = appointmentRepository.find(appointmentRepository.QUERIES.dateIs(date))
        assertThat(actualAppointment).isNotNull()
        assertThat(actualAppointment!!.scheduledTime).isEqualTo(date)

        val actualClient = clientRepository.get(actualAppointment.clientId)
        assertThat(actualClient).isNotNull()
        assertThat(actualClient!!.name).isEqualTo(client)
    }
}