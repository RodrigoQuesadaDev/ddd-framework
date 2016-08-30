package com.aticosoft.appointments.mobile.business.domain.model.client.validation

import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidationException
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.querydsl.core.types.Path

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
/*internal*/ abstract class ClientValidator<X : ClientValidationException>(
        createException: (String) -> X, vararg validatedFields: Path<*>
) : PersistableObjectValidator<Client, X>(createException, *validatedFields)

/*internal*/ open class ClientValidationException(message: String) : PersistableObjectValidationException(message)