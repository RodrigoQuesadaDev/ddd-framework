package com.rodrigodev.common.spec.story.converter

import com.rodrigodev.common.concurrent.secsToMillis
import org.jbehave.core.steps.ParameterConverters.NumberConverter
import org.jbehave.core.steps.ParameterConverters.ParameterConvertionFailed
import org.joda.time.Duration
import org.joda.time.Duration.millis
import java.lang.reflect.Type
import kotlin.text.Regex

/**
* Created by Rodrigo Quesada on 29/10/15.
*/
class DurationConverter : ParameterConverterBase<Duration>(Duration::class.java) {
    private companion object {
        val TIME_PATTERN = Regex("(\\d+(?:\\.\\d+)?)\\s?(.+)")
        val NUMBER_CONVERTER = NumberConverter()
    }

    private object TimeUnitPatterns {
        val SECONDS = Regex("(?:secs?)|s")
    }

    override fun convertValue(value: String, type: Type): Duration {
        try {
            val matchResult = TIME_PATTERN.matchEntire(value)
            val timeValueString = matchResult!!.groups[1]!!.value
            val timeUnit = matchResult.groups[2]!!.value

            val timeValue: Double = NUMBER_CONVERTER.convertValue(timeValueString, Double::class.java) as Double
            return when {
                timeUnit.matches(TimeUnitPatterns.SECONDS) -> {
                    millis(timeValue.secsToMillis())
                }
                else -> {
                    throw IllegalArgumentException("Time unit doesn't match any of the patterns.")
                }
            }
        }
        catch(e: Exception) {
            throw ParameterConvertionFailed("Unable to convert value to duration.", e)
        }
    }
}