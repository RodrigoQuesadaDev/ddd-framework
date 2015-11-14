package com.rodrigodev.common.spec.story.converter

import org.jbehave.core.steps.ParameterConverters
import java.lang.reflect.Type

/**
* Created by Rodrigo Quesada on 31/10/15.
*/
class CustomNumberListConverter : ParameterConverters.NumberListConverter() {

    //TODO remove suppression when KT-8619 is fixed
    @Suppress("IMPLICIT_CAST_TO_UNIT_OR_ANY")
    override fun convertValue(value: String, type: Type): Any = if (value.trim().isEmpty()) arrayListOf<Number>() else super.convertValue(value, type)
}