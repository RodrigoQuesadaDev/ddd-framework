package com.aticosoft.appointments.mobile.business.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
interface ConfigurationQueries {

    fun retrieve(): UniqueQuery<Configuration>
}