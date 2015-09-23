package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.UniqueQuery
import org.joda.time.DateTime

/**
 * Created by rodrigo on 05/09/15.
 */
internal interface AppointmentRepository : Repository<Appointment, AppointmentQueries>

//TODO move inside AppointmentRepository as inner class (therefore not requiring context argument) when https://youtrack.jetbrains.com/issue/KT-9328 is fixed
/*internal*/ interface AppointmentQueries : Repository.Queries {

    fun dateIs(date: DateTime): UniqueQuery<Appointment?>
}