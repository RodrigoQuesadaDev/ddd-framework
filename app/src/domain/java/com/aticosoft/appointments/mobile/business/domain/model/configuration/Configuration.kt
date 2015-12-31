package com.aticosoft.appointments.mobile.business.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.google.auto.factory.Provided
import org.joda.time.Duration
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@PersistenceCapable
//@AutoFactory
/*internal*/ class Configuration(@Provided context: Entity.Context) : Entity(context) {

    var maxConcurrentAppointments: Int = 0
    var timeSlotDuration: Duration = Duration.ZERO

    init {
        reset()
    }

    fun reset() {
        timeSlotDuration = Duration.standardMinutes(15)
    }
}
