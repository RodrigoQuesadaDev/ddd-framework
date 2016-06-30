package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.querydsl.core.types.Path

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
enum class AppointmentQueryView(override vararg val fields: Path<*>) : QueryView {
    DEFAULT(QAppointment.appointment._scheduledTime);

    override lateinit var _filterTypes: Sequence<Class<out Entity>>
    override lateinit var fetchGroupName: String
}