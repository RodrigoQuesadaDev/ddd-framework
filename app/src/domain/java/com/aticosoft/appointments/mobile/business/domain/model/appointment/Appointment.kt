package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import org.joda.time.DateTime
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by rodrigo on 26/07/15.
 */
@PersistenceCapable
class Appointment(
        id: Long,
        clientId: Long,
        scheduledTime: DateTime
) : Entity(id) {

    var clientId: Long = clientId
        private set
    var scheduledTime: DateTime = scheduledTime
        private set
}