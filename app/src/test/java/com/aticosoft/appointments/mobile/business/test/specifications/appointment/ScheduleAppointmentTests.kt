package com.aticosoft.appointments.mobile.business.test.specifications.appointment

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.test.specifications.BaseSpecification
import org.assertj.core.api.Assertions.assertThat
import org.joda.time.DateTime
import org.junit.Test
import javax.jdo.JDOHelper

/**
 * Created by rodrigo on 10/09/15.
 */
public class ScheduleAppointmentTests : BaseSpecification() {

    @Test
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
