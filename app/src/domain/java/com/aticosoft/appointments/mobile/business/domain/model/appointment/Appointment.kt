package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.google.auto.factory.Provided
import org.joda.time.DateTime
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 26/07/15.
 */
@PersistenceCapable
//@AutoFactory
class Appointment protected constructor(
        @Provided context: Entity.Context,
        clientId: Long,
        scheduledTime: DateTime
) : Entity(context) {

    var clientId = clientId
        private set
    var scheduledTime = scheduledTime
        private set
}