package com.aticosoft.appointments.mobile.business.domain.model.appointment.validation

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidationException
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.querydsl.core.types.Path

/**
 * Created by Rodrigo Quesada on 24/06/16.
 */
/*internal*/ abstract class AppointmentValidator(
        createException: (String) -> AppointmentValidationException, vararg validatedFields: Path<*>
) : PersistableObjectValidator<Appointment, AppointmentValidationException>(createException, *validatedFields)

/*internal*/ open class AppointmentValidationException(message: String) : PersistableObjectValidationException(message)