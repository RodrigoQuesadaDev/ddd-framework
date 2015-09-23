package com.aticosoft.appointments.mobile.business.domain.specs.stories.appointment

import com.rodrigodev.specs.stories.common.ParameterConverterBase
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.lang.reflect.Type

/**
 * Created by rodrigo on 21/09/15.
 */
internal class AppointmentTimeConverter : ParameterConverterBase<DateTime>() {

    private companion object {
        val timeFormater = DateTimeFormat.forPattern("yyyy-MM-dd 'at' HH:mm")
    }

    override protected fun supportedType() = DateTime::class.java

    override fun convertValue(value: String, type: Type) = timeFormater.parseDateTime(value)
}