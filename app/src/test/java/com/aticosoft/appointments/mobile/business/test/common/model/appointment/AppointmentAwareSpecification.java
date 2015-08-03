package com.aticosoft.appointments.mobile.business.test.common.model.appointment;

import com.aticosoft.appointments.mobile.business.domain.application.AppointmentService;
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository;

/**
 * Created by rodrigo on 22/08/15.
 */
public interface AppointmentAwareSpecification {

    void appointmentService(AppointmentService appointmentService);
    void appointmentRepository(AppointmentRepository appointmentRepository);
}
