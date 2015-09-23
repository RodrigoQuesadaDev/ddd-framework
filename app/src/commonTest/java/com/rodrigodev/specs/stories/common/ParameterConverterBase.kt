package com.rodrigodev.specs.stories.common

import org.jbehave.core.steps.ParameterConverters
import java.lang.reflect.Type

/**
 * Created by rodrigo on 21/09/15.
 */
abstract class ParameterConverterBase<out T> : ParameterConverters.ParameterConverter {

    final override fun accept(type: Type) = if (type is Class<*>) type.isAssignableFrom(supportedType()) else false

    protected abstract fun supportedType(): Class<out T>

    override abstract fun convertValue(value: String, type: Type): T
}