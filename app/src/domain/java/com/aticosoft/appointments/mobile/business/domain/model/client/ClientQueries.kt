package com.aticosoft.appointments.mobile.business.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.model.common.EntityQueries
import com.aticosoft.appointments.mobile.business.domain.model.common.ListQuery

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
interface ClientQueries : EntityQueries<Client> {

    fun nameLike(name: String): ListQuery<Client>
}