package com.rodrigodev.common.spec.story.converter

import org.jbehave.core.steps.ParameterConverters
import java.lang.reflect.Type

/**
 * Created by Rodrigo Quesada on 16/11/15.
 */
internal class CustomNumberConverter : ParameterConverters.NumberConverter() {
    private companion object {
        val NULL_VALUE = "null"
    }

    override fun convertValue(value: String, type: Type): Any? = if (!value.equals(NULL_VALUE, ignoreCase = true)) super.convertValue(value, type) else null
}