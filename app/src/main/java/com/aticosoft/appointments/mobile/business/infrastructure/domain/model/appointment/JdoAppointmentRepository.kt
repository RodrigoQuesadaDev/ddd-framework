package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment
import com.aticosoft.appointments.mobile.business.domain.model.common.QueryEntity
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepositoryBase
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 25/09/15.
 */
@Singleton
/*internal*/ class JdoAppointmentRepository @Inject constructor(context: PersistenceContext) : JdoRepositoryBase<Appointment>(context), AppointmentRepository {

    override val queryEntity = object : QueryEntity<Appointment, QAppointment>(QAppointment.appointment) {
        override val id = entityPath.id
    }
}