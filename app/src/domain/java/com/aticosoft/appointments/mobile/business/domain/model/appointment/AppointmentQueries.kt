package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.common.EntityQueries
import com.aticosoft.appointments.mobile.business.domain.model.common.ListQuery
import org.joda.time.Interval

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
interface AppointmentQueries : EntityQueries<Appointment> {

    fun timeIs(time: Interval): ListQuery<Appointment>
}