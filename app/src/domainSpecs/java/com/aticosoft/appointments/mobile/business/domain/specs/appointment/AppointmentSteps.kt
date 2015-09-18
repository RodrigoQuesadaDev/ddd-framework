package com.aticosoft.appointments.mobile.business.domain.specs.appointment

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.test.common.model.appointment.AppointmentAwareStory
import org.assertj.core.api.Assertions.*
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.joda.time.DateTime
import javax.jdo.JDOHelper

/**
 * Created by rodrigo on 17/09/15.
 */
class AppointmentSteps(domainStory: DomainStory) : AppointmentAwareStory by domainStory {

    @Given("an empty calendar")
    fun anEmptyCalendar() {
    }

    @When("the owner schedules an appointment for <client> on <date>")
    fun theOwnerSchedulesAnAppointment() {
    }

    @Then("an appointment is scheduled for <client> on <date>")
    fun appointmentIsScheduled() {
        val time = DateTime(1234, 3, 7, 11, 13)
        val expectedAppointment = Appointment(time)

        val actualAppointment = appointmentService.scheduleAppointment(time)

        assertThat(actualAppointment).isEqualTo(expectedAppointment)
        assertThat(appointmentRepository.get(actualAppointment.id)).isEqualTo(expectedAppointment)

        assertThat(JDOHelper.isDirty(actualAppointment)).isFalse()
        actualAppointment.scheduledTime = DateTime.now()
        assertThat(JDOHelper.isDirty(actualAppointment)).isTrue()
    }
}