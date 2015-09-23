package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment
import com.aticosoft.appointments.mobile.business.domain.model.common.QueryEntity
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepositoryBase
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 25/09/15.
 */
@Singleton
/*internal*/ class JdoAppointmentRepository @Inject constructor(context: PersistenceContext) : JdoRepositoryBase<Appointment, AppointmentQueries>(context), AppointmentRepository {

    override val QUERIES = JdoAppointmentQueries(context)

    override val queryEntity = object : QueryEntity<Appointment, QAppointment>(QAppointment.appointment) {
        override val id = entityPath.id
    }
}

internal class JdoAppointmentQueries(val context: PersistenceContext) : AppointmentQueries {

    override fun dateIs(date: DateTime) = UniqueQuery {
        val a = QAppointment.appointment
        context.queryFactory.selectFrom(a).where(a.scheduledTime.eq(date)).fetchOne()
    }
}