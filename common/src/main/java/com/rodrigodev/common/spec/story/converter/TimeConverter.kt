package com.rodrigodev.common.spec.story.converter

import org.joda.time.format.DateTimeFormat

/**
 * Created by rodrigo on 05/10/15.
 */
abstract class TimeConverter<out T>(
        supportedType: Class<out T>,
        timePattern: String
) : ParameterConverterBase<T>(supportedType) {

    protected val timeFormatter = DateTimeFormat.forPattern(timePattern)
}