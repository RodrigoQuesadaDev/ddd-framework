package com.aticosoft.appointments.mobile.business.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.model.common.EntityQueries
import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
interface ConfigurationQueries : EntityQueries<Configuration> {

    val RETRIEVE: UniqueQuery<Configuration>
}