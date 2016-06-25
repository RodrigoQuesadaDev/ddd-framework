package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 25/09/15.
 */
@Singleton
/*internal*/ class JdoAppointmentRepository @Inject protected constructor() : JdoRepository<Appointment>(QAppointment.appointment)