package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.common.values.Absent
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.embedded.EmbeddedDelegate
import com.aticosoft.appointments.mobile.business.domain.model.common.embedded.EmbeddedDelegate.EmbeddedProperty
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
        @Provided context: Entity.Context?,
        clientId: String,
        scheduledTime: Interval
) : Entity(context) {
    private constructor() : this(Absent.entityContext, Absent.string, Absent.interval)

    var clientId = clientId
        private set

    private var _scheduledTime = EmbeddedInterval(scheduledTime)

    val scheduledTime: Interval by EmbeddedDelegate(EmbeddedProperty({ _scheduledTime }, { _scheduledTime = it }), EmbeddedInterval.Delegator)
}