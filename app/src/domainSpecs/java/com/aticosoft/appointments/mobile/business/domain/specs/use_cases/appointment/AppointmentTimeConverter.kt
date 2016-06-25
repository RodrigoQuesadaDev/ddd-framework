@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment

import com.rodrigodev.common.spec.story.converter.ParameterConverterBase
import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.format.DateTimeFormat
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 21/09/15.
 */
@Singleton
internal class AppointmentTimeConverter @Inject constructor() : ParameterConverterBase<Interval>(Interval::class.java) {
    private companion object {
        val INTERVAL_FORMAT = Regex("([^\\s]+) from ([^\\s]+) to ([^\\s]+)", RegexOption.IGNORE_CASE)
    }

    private val timeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm")

    override fun convertValue(value: String, type: Type): Interval {
        return with(INTERVAL_FORMAT.matchEntire(value)!!) {
            val date = groups[1]
            val startTime = groups[2].parseTimeUsing(date)
            val endTime = groups[3].parseTimeUsing(date)
            Interval(startTime, endTime)
        }
    }

    private inline fun MatchGroup?.parseTimeUsing(date: MatchGroup?): DateTime {
        this!!
        date!!
        return timeFormatter.parseDateTime("${date.value} $value")
    }
}