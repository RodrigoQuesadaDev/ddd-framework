package com.aticosoft.appointments.mobile.business.domain.model.client.validation

import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.ValidationException
import com.querydsl.core.types.Path

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
/*internal*/ abstract class ClientValidator<X : ClientValidationException>(
        createException: (String) -> X, vararg validatedFields: Path<*>
) : EntityValidator<Client, X>(createException, *validatedFields) {

    override val entityType = Client::class.java
}

/*internal*/ open class ClientValidationException(message: String) : ValidationException(message)