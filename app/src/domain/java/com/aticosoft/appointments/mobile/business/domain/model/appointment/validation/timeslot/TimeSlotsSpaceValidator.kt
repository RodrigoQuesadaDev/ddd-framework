package com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.timeslot

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.appointment.QAppointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.AppointmentValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.rodrigodev.common.collection.arrayOfZeros
import org.joda.time.DateTime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 07/07/16.
 */
@Singleton
class TimeSlotsSpaceValidator @Inject protected constructor(
        private val appointmentRepository: Repository<Appointment>,
        private val appointmentQueries: AppointmentQueries
) : AppointmentValidator(
        ::TimeSlotsSpaceException,
        QAppointment.appointment._scheduledTime
) {
    override val entityType = Appointment::class.java

    override fun Appointment.errorMessage() = "Not enough time slots available for scheduling appointment at time \"$scheduledTime\"."

    override fun Appointment.isValid(): Boolean {
        var valid = true

        //--Configuration--
        val configuration = retrieveConfiguration()
        val maxConcurrency = configuration.maxConcurrentAppointments
        val slotDurationMillis = configuration.timeSlotDuration.millis
        //--Scheduled Time--
        val timeStart = scheduledTime.startMillis
        val timeEnd = scheduledTime.endMillis
        //--Slots--
        val requiredSlotsSize: Int = Math.round((timeEnd - timeStart).toDouble() / slotDurationMillis).toInt()
        val requiredSlots = arrayOfZeros(requiredSlotsSize)

        //region Utils
        fun DateTime.slotIndex(): Int {
            val index = Math.round((millis - timeStart).toDouble() / slotDurationMillis).toInt()
            return when {
                index < 0 -> 0
                index >= requiredSlotsSize -> requiredSlotsSize
                else -> index
            }
        }
        //endregion

        val overlappingAppointments = appointmentRepository.find(appointmentQueries.timeOverlaps(scheduledTime))
        overlappingAppointments.forEach mainLoop@ {
            val overlappingTime = it.scheduledTime
            for (i in overlappingTime.start.slotIndex() until overlappingTime.end.slotIndex()) {
                if (++requiredSlots[i] == maxConcurrency) {
                    valid = false
                    return@mainLoop
                }
            }
        }

        return valid
    }
}

//region Other Classes
/*internal*/ class TimeSlotsSpaceException(message: String) : TimeSlotsValidationException(message)
//endregion