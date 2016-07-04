package com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.timeslot

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.AppointmentValidator
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 07/07/16.
 */
@Singleton
class TimeSlotsAlignmentValidator @Inject protected constructor()
: AppointmentValidator(::TimeSlotsAlignmentException, QAppointment.appointment._scheduledTime) {

    override fun Appointment.errorMessage() = "Scheduled time \"$scheduledTime\" is not aligned to time slots."

    override fun Appointment.isValid(): Boolean {
        val slotDuration = retrieveConfiguration().timeSlotDuration

        //region Utils
        fun Int.isAlignedToTimeSlots() = this % slotDuration.standardMinutes == 0L
        //endregion

        return with(scheduledTime) {
            start.minuteOfDay.isAlignedToTimeSlots() && end.minuteOfDay.isAlignedToTimeSlots()
        }
    }
}

//region Other Classes
/*internal*/ class TimeSlotsAlignmentException(message: String) : TimeSlotsValidationException(message)
//endregion