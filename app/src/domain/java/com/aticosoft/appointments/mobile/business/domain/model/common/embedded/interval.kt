@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.embedded

import com.querydsl.core.types.Predicate
import org.joda.time.DateTime
import org.joda.time.Interval
import javax.jdo.annotations.EmbeddedOnly

/**
 * Created by Rodrigo Quesada on 01/07/16.
 */
@EmbeddedOnly
class EmbeddedInterval(scheduledTime: Interval) {
    var start: DateTime = scheduledTime.start
    var end: DateTime = scheduledTime.end
}

//region Extensions
inline fun QEmbeddedInterval.eq(time: Interval): Predicate = start.eq(time.start).and(end.eq(time.end))
//endregion