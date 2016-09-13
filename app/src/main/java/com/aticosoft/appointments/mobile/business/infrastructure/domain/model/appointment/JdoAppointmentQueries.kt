package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment
import com.aticosoft.appointments.mobile.business.domain.model.common.embedded.eq
import com.aticosoft.appointments.mobile.business.domain.model.common.embedded.overlaps
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.ListQuery
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoQueries
import org.joda.time.Interval
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
@Singleton
/*internal*/ class JdoAppointmentQueries @Inject protected constructor() : JdoQueries<Appointment>(), AppointmentQueries {

    override fun timeIs(time: Interval) = ListQuery {
        val a = QAppointment.appointment
        context.queryFactory.selectFrom(a).where(a._scheduledTime.eq(time)).fetch()
    }

    override fun timeOverlaps(range: Interval) = ListQuery {
        val a = QAppointment.appointment
        context.queryFactory.selectFrom(a).where(a._scheduledTime.overlaps(range)).fetch()
    }
}