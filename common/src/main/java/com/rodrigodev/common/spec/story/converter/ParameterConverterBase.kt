package com.rodrigodev.common.spec.story.converter

import org.jbehave.core.steps.ParameterConverters
import java.lang.reflect.Type

/**
 * Created by rodrigo on 21/09/15.
 */
abstract class ParameterConverterBase<out T>(val supportedType: Class<out T>) : ParameterConverters.ParameterConverter {

    final override fun accept(type: Type) = if (type is Class<*>) type.isAssignableFrom(supportedType) else false

    override abstract fun convertValue(value: String, type: Type): T
}