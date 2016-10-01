package com.rodrigodev.common.spec.story.converter

import com.rodrigodev.common.concurrent.minsToMillis
import com.rodrigodev.common.concurrent.secsToMillis
import com.rodrigodev.common.spec.story.converter.DurationConverter.TimeUnitPatterns.MINUTES
import com.rodrigodev.common.spec.story.converter.DurationConverter.TimeUnitPatterns.SECONDS
import org.jbehave.core.steps.ParameterConverters.NumberConverter
import org.jbehave.core.steps.ParameterConverters.ParameterConvertionFailed
import org.joda.time.Duration
import java.lang.reflect.Type

/**
 * Created by Rodrigo Quesada on 29/10/15.
 */
class DurationConverter : ParameterConverterBase<Duration>(Duration::class.java) {
    private companion object {
        val DURATION_PATTERN = Regex("(\\d+(?:\\.\\d+)?)\\s?(.+)", RegexOption.IGNORE_CASE)
        val NUMBER_CONVERTER = NumberConverter()
    }

    protected object TimeUnitPatterns {
        val SECONDS = Regex("s(?:ec(?:ond)?s?)?", RegexOption.IGNORE_CASE)
        val MINUTES = Regex("m(?:in(?:ute)?s?)?", RegexOption.IGNORE_CASE)
    }

    override fun convertValue(value: String, type: Type): Duration {
        try {
            with(DURATION_PATTERN.matchEntire(value)!!) {
                val timeValue: Double = NUMBER_CONVERTER.convertValue(groups[1]!!.value, Double::class.java) as Double
                val timeUnit = groups[2]!!.value
                return Duration.millis(when {
                    timeUnit.matches(SECONDS) -> timeValue.secsToMillis()
                    timeUnit.matches(MINUTES) -> timeValue.minsToMillis()
                    else -> throw IllegalArgumentException("Invalid time unit value: \"$timeUnit\"")
                })
            }
        }
        catch(e: Exception) {
            throw ParameterConvertionFailed("Unable to convert value to duration.", e)
        }
    }
}