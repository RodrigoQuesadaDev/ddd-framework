@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.embedded

import com.querydsl.core.types.dsl.BooleanExpression
import org.joda.time.DateTime
import org.joda.time.Interval
import javax.jdo.annotations.EmbeddedOnly

/**
 * Created by Rodrigo Quesada on 01/07/16.
 */
@EmbeddedOnly
internal class EmbeddedInterval(scheduledTime: Interval) {
    var start: DateTime = scheduledTime.start
    var end: DateTime = scheduledTime.end

    object Delegator : EmbeddedDelegate.Delegator<Interval, EmbeddedInterval>(
            { Interval(start, end) },
            ::EmbeddedInterval
    )
}

//region Extensions
inline fun QEmbeddedInterval.eq(time: Interval): BooleanExpression = start.eq(time.start).and(end.eq(time.end))

inline fun QEmbeddedInterval.overlaps(range: Interval): BooleanExpression = startOverlaps(range).or(endOverlaps(range)).or(inside(range))

inline fun QEmbeddedInterval.startOverlaps(range: Interval): BooleanExpression = start.goe(range.start).and(start.lt(range.end))

inline fun QEmbeddedInterval.endOverlaps(range: Interval): BooleanExpression = end.loe(range.end).and(end.gt(range.start))

inline fun QEmbeddedInterval.inside(range: Interval): BooleanExpression = start.lt(range.start).and(end.gt(range.end))
//endregion