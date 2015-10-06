package com.rodrigodev.specs.stories.common.converter

import org.joda.time.format.DateTimeFormat

/**
 * Created by rodrigo on 05/10/15.
 */
internal abstract class TimeConverter<out T>(
        supportedType: Class<out T>,
        timePattern: String
) : ParameterConverterBase<T>(supportedType) {

    protected val timeFormatter = DateTimeFormat.forPattern(timePattern)
}