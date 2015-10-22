package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.common.queries.UniqueQuery
import org.joda.time.DateTime

/**
 * Created by rodrigo on 19/10/15.
 */
interface AppointmentQueries {

    fun dateIs(date: DateTime): UniqueQuery<Appointment>
}