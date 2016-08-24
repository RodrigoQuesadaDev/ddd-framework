package com.aticosoft.appointments.mobile.business.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.querydsl.core.types.Path

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
enum class AppointmentQueryView(override vararg val fields: Path<*>) : QueryView {
    DEFAULT(QAppointment.appointment._scheduledTime);

    override lateinit var _filterTypes: Sequence<Class<out PersistableObject<*>>>
    override lateinit var fetchGroupName: String
}