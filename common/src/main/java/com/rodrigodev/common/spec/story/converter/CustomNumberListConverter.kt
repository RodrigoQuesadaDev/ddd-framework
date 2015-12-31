package com.rodrigodev.common.spec.story.converter

import org.jbehave.core.steps.ParameterConverters
import java.lang.reflect.Type

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
class CustomNumberListConverter : ParameterConverters.NumberListConverter() {

    override fun convertValue(value: String, type: Type): Any = if (value.trim().isEmpty()) arrayListOf<Number>() else super.convertValue(value, type)
}
