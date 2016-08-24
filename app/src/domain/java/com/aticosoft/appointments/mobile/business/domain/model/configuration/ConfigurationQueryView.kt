package com.aticosoft.appointments.mobile.business.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
internal enum class ConfigurationQueryView : QueryView {
    ;

    override lateinit var _filterTypes: Sequence<Class<out PersistableObject<*>>>
    override lateinit var fetchGroupName: String
}