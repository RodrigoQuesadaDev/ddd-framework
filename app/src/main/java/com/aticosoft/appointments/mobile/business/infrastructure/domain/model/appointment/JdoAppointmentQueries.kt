package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment
import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
@Singleton
internal class JdoAppointmentQueries @Inject constructor(private val context: PersistenceContext) : AppointmentQueries {

    override fun dateIs(date: DateTime) = UniqueQuery {
        val a = QAppointment.appointment
        context.queryFactory.selectFrom(a).where(a.scheduledTime.eq(date)).fetchOne()
    }
}