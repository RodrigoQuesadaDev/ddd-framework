package com.aticosoft.appointments.mobile.business.domain.model.appointment.validation

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.ValidationException
import com.querydsl.core.types.Path

/**
 * Created by Rodrigo Quesada on 24/06/16.
 */
/*internal*/ abstract class AppointmentValidator(
        createException: (String) -> AppointmentValidationException, vararg validatedFields: Path<*>
) : EntityValidator<Appointment, AppointmentValidationException>(createException, *validatedFields) {

    override val entityType = Appointment::class.java
}

/*internal*/ open class AppointmentValidationException(message: String) : ValidationException(message)