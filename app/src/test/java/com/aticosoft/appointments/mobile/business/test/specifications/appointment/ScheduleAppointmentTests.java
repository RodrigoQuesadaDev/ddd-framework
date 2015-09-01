package com.aticosoft.appointments.mobile.business.test.specifications.appointment;

import com.aticosoft.appointments.mobile.business.domain.application.AppointmentService;
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment;
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository;
import com.aticosoft.appointments.mobile.business.test.specifications.BaseSpecification;

import org.joda.time.DateTime;
import org.junit.Test;

import javax.jdo.JDOHelper;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by rodrigo on 26/07/15.
 */
public class ScheduleAppointmentTests extends BaseSpecification {

    @Test
    public void appointmentIsScheduled() {
        DateTime time = new DateTime(1234, 3, 7, 11, 13);
        Appointment expectedAppointment = new Appointment(time);

        Appointment actualAppointment = appointmentService.scheduleAppointment(time);

        assertThat(actualAppointment).isEqualTo(expectedAppointment);
        assertThat(appointmentRepository.get(actualAppointment.getId())).isEqualTo(expectedAppointment);

        assertThat(JDOHelper.isDirty(actualAppointment)).isFalse();
        actualAppointment.setScheduledTime(DateTime.now());
        assertThat(JDOHelper.isDirty(actualAppointment)).isTrue();
    }

    private AppointmentService appointmentService;
    private AppointmentRepository appointmentRepository;

    @Override
    public void setAppointmentService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Override
    public void setAppointmentRepository(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
}
