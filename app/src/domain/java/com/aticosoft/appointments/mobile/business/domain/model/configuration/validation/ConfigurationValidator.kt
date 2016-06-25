package com.aticosoft.appointments.mobile.business.domain.model.client.validation

import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.ValidationException
import com.aticosoft.appointments.mobile.business.domain.model.configuration.Configuration
import com.querydsl.core.types.Path

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
/*internal*/ abstract class ConfigurationValidator<X : ConfigurationValidationException>(
        createException: (String) -> X, vararg validatedFields: Path<*>
) : EntityValidator<Configuration, X>(createException, *validatedFields) {

    override val entityType = Configuration::class.java
}

/*internal*/ open class ConfigurationValidationException(message: String) : ValidationException(message)