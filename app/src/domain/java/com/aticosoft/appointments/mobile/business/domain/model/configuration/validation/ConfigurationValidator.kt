package com.aticosoft.appointments.mobile.business.domain.model.client.validation

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidationException
import com.aticosoft.appointments.mobile.business.domain.model.configuration.Configuration
import com.querydsl.core.types.Path

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
/*internal*/ abstract class ConfigurationValidator<X : ConfigurationValidationException>(
        createException: (String) -> X, vararg validatedFields: Path<*>
) : PersistableObjectValidator<Configuration, X>(createException, *validatedFields) {

    override val objectType = Configuration::class.java
}

/*internal*/ open class ConfigurationValidationException(message: String) : PersistableObjectValidationException(message)