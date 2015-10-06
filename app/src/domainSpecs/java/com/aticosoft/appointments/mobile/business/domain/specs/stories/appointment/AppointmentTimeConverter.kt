package com.aticosoft.appointments.mobile.business.domain.specs.stories.appointment

import com.rodrigodev.specs.stories.common.converter.TimeConverter
import org.joda.time.DateTime
import java.lang.reflect.Type

/**
 * Created by rodrigo on 21/09/15.
 */
internal class AppointmentTimeConverter : TimeConverter<DateTime>(DateTime::class.java, "yyyy-MM-dd 'at' HH:mm") {

    override fun convertValue(value: String, type: Type) = timeFormatter.parseDateTime(value)
}