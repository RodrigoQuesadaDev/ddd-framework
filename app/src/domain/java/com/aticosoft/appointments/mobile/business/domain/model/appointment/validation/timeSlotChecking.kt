@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.appointment.validation

import com.aticosoft.appointments.mobile.business.domain.model.configuration.Configuration
import com.rodrigodev.common.preconditions.checkArgument
import org.joda.time.Duration
import org.joda.time.Interval

/**
 * Created by Rodrigo Quesada on 26/12/15.
 */
private val START = "Start"
private val END = "End"

private fun errorMessage(timeName: String) = "$timeName time doesn't match the time slot configuration."

public fun checkIntervalMatchesTimeSlots(interval: Interval, configuration: Configuration): Unit = with(interval) {
    val slotDuration = configuration.timeSlotDuration
    start.minuteOfDay.checkItMatchesTimeSlot(START, slotDuration)
    end.minuteOfDay.checkItMatchesTimeSlot(END, slotDuration)
}

private inline fun Int.checkItMatchesTimeSlot(timeName: String, slotDuration: Duration) {
    checkArgument({ this % slotDuration.standardMinutes == 0L }, errorMessage(timeName))
}