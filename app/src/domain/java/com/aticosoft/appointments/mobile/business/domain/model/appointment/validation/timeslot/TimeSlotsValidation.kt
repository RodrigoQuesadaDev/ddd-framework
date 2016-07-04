package com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.timeslot

import com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.AppointmentValidationException

/**
 * Created by Rodrigo Quesada on 24/06/16.
 */
/*internal*/ abstract class TimeSlotsValidationException(message: String) : AppointmentValidationException(message)