package com.aticosoft.appointments.mobile.business.domain.model.appointment.validation

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 24/06/16.
 */
@Singleton
/*internal*/ class TimeSlotsValidation @Inject protected constructor(
        private val timeSlotsAlignmentValidator: TimeSlotsAlignmentValidator
) {

    val validators = setOf(timeSlotsAlignmentValidator)

    @Singleton
    class TimeSlotsAlignmentValidator @Inject protected constructor() : AppointmentValidator<TimeSlotValidationException>(
            ::TimeSlotValidationException, QAppointment.appointment.scheduledTime
    ) {

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

    /*class TimeSlotsSpaceValidator : EntityValidator<Appointment>() {
        override val entityType = Appointment::class.java

        override fun Appointment.errorMessage() = "Not enough time slots available for scheduling appointment at time \"$scheduledTime\"."

        override fun Appointment.isValid(): Boolean {
            throw UnsupportedOperationException()
        }
    }*/
}

//region Other Classes
/*internal*/ class TimeSlotValidationException(message: String) : AppointmentValidationException(message)
//endregion