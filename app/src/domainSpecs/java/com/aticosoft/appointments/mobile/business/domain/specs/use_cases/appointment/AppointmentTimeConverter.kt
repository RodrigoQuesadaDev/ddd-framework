package com.aticosoft.appointments.mobile.business.domain.specs.use_cases.appointment

import com.rodrigodev.common.spec.story.converter.TimeConverter
import org.joda.time.DateTime
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

/**
* Created by Rodrigo Quesada on 21/09/15.
*/
@Singleton
internal class AppointmentTimeConverter @Inject constructor() : TimeConverter<DateTime>(DateTime::class.java, "yyyy-MM-dd 'at' HH:mm") {

    override fun convertValue(value: String, type: Type) = timeFormatter.parseDateTime(value)
}