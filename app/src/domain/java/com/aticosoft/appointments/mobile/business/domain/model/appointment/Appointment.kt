package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.common.values.Absent
import com.aticosoft.appointments.mobile.business.domain.model.common.Delegates.embedded
import com.aticosoft.appointments.mobile.business.domain.model.common.embedded.EmbeddedInterval
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
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

    //TODO this pattern is actually pretty shitty... no need for generated delegate, do it manually!
    private var _scheduledTime = EmbeddedInterval(scheduledTime)
    val scheduledTime: Interval by embedded(EmbeddedInterval.Delegator,
            get = { _scheduledTime },
            set = { _scheduledTime = it }
    )
}
