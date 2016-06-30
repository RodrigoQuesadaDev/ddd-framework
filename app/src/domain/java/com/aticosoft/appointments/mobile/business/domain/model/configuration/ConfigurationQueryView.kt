package com.aticosoft.appointments.mobile.business.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
internal enum class ConfigurationQueryView : QueryView {
    ;

    override lateinit var _filterTypes: Sequence<Class<out Entity>>
    override lateinit var fetchGroupName: String
}