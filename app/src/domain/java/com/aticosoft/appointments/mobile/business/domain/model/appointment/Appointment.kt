package com.aticosoft.appointments.mobile.business.domain.model.appointment

import org.joda.time.DateTime
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by rodrigo on 26/07/15.
 */
PersistenceCapable
data class Appointment(var scheduledTime: DateTime) {

    var id: Long = 0
}
