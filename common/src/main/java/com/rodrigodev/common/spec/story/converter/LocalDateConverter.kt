package com.rodrigodev.common.spec.story.converter

import org.joda.time.LocalDate
import java.lang.reflect.Type

/**
 * Created by rodrigo on 05/10/15.
 */
internal class LocalDateConverter : TimeConverter<LocalDate>(LocalDate::class.java, "yyyy-MM-dd") {

    override fun convertValue(value: String, type: Type) = timeFormatter.parseLocalDate(value)
}