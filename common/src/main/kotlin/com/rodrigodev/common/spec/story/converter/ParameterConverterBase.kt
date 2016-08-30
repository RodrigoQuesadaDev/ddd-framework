package com.rodrigodev.common.spec.story.converter

import com.rodrigodev.common.reflection.isSuperOfOrSameAs
import org.jbehave.core.steps.ParameterConverters
import java.lang.reflect.Type

/**
 * Created by Rodrigo Quesada on 21/09/15.
 */
abstract class ParameterConverterBase<out T>(val supportedType: Class<out T>) : ParameterConverters.ParameterConverter {

    final override fun accept(type: Type) = type is Class<*> && type.isSuperOfOrSameAs(supportedType)

    override abstract fun convertValue(value: String, type: Type): T
}