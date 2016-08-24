package com.aticosoft.appointments.mobile.business.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.UniqueQuery

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
interface ConfigurationQueries : Queries<Configuration> {

    val RETRIEVE: UniqueQuery<Configuration>
}