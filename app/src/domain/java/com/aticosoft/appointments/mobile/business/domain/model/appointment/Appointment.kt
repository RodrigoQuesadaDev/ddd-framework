package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.embedded.EmbeddedInterval
import com.google.auto.factory.Provided
import org.joda.time.Interval
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 26/07/15.
 */
@PersistenceCapable
//@AutoFactory
class Appointment protected constructor(
        @Provided context: Entity.Context,
        clientId: String,
        scheduledTime: Interval
) : Entity(context) {

    var clientId = clientId
        private set

    private var _scheduledTime = EmbeddedInterval(scheduledTime)

    //TODO change this (use delegate...)
    val scheduledTime: Interval
        get() = Interval(_scheduledTime.start, _scheduledTime.end)
}