package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery
import org.joda.time.DateTime

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
interface AppointmentQueries {

    fun dateIs(date: DateTime): UniqueQuery<Appointment>
}