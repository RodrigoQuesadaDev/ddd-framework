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
        var clientId: Long,
        var scheduledTime: DateTime
) : Entity(id)