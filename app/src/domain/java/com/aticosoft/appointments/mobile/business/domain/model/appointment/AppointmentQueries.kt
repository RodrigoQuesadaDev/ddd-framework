package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.ListQuery
import org.joda.time.Interval

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
interface AppointmentQueries : Queries<Appointment> {

    fun timeIs(time: Interval): ListQuery<Appointment>

    fun timeOverlaps(range: Interval): ListQuery<Appointment>
}