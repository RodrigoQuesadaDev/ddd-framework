package com.aticosoft.appointments.mobile.business.domain.model.appointment.validation

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.querydsl.core.types.Path
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
    class TimeSlotsAlignmentValidator @Inject protected constructor() : TimeSlotsValidator(
            QAppointment.appointment._scheduledTime
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

    @Singleton
    class TimeSlotsSpaceValidator @Inject protected constructor(
            private val appointmentRepository: Repository<Appointment>,
            private val appointmentQueries: AppointmentQueries
    ) : TimeSlotsValidator(
            QAppointment.appointment._scheduledTime
    ) {
        override val entityType = Appointment::class.java

        override fun Appointment.errorMessage() = "Not enough time slots available for scheduling appointment at time \"$scheduledTime\"."

        override fun Appointment.isValid(): Boolean {
            val configuration = retrieveConfiguration()
            val maxConcurrency = configuration.maxConcurrentAppointments
            val requiredSlotNumber: Long = Math.round(scheduledTime.toDurationMillis().toDouble() / configuration.timeSlotDuration.millis)
            val requiredSlots = kotlin.arrayOfNulls<Int>(requiredSlotNumber.toInt())

            //val overlappingAppointments = appointmentRepository.find(appointmentQueries.timeIn(scheduledTime))
            return true
        }
    }
}

//region Other Classes
/*internal*/ abstract class TimeSlotsValidator(vararg validatedFields: Path<*>)
: AppointmentValidator<TimeSlotValidationException>(::TimeSlotValidationException, *validatedFields)

/*internal*/ class TimeSlotValidationException(message: String) : AppointmentValidationException(message)
//endregion