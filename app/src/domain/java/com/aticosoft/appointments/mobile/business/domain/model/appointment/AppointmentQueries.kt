package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.common.ListQuery
import org.joda.time.Interval

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
interface AppointmentQueries {

    fun timeIs(time: Interval): ListQuery<Appointment>
}