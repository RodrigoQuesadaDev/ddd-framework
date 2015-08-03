package com.aticosoft.appointments.mobile.business.domain.application;

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment;
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository;

import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by rodrigo on 26/07/15.
 */
@Singleton
public class AppointmentService {

    @Inject AppointmentRepository appointmentRepository;

    @Inject
    public AppointmentService() {
    }

    public Appointment scheduleAppointment(DateTime time) {
        Appointment appointment = new Appointment(time);
        appointmentRepository.add(appointment);
        return appointment;
    }
}
