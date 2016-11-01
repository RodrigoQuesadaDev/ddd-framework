package com.rodrigodev.common.spec.story.converter

import com.rodrigodev.common.string.parseUnsignedBinaryLong
import com.rodrigodev.common.string.parseUnsignedHexLong
import org.jbehave.core.steps.ParameterConverters
import java.lang.reflect.Type

/**
 * Created by Rodrigo Quesada on 16/11/15.
 */
internal class CustomNumberConverter : ParameterConverters.NumberConverter() {
    private companion object {
        val NULL_VALUE = "null"
    }

    override fun convertValue(value: String, type: Type): Any? {
        return when {
            value.equals(NULL_VALUE, ignoreCase = true) -> null
            value.startsWith("0x") -> value.parseUnsignedHexLong()
            value.startsWith("0b") -> value.parseUnsignedBinaryLong()
            else -> super.convertValue(value, type)
        }
    }
}