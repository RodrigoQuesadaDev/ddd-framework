package com.aticosoft.appointments.mobile.business.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.ListQuery

/**
 * Created by Rodrigo Quesada on 19/10/15.
 */
interface ClientQueries : Queries<Client> {

    fun nameLike(name: String): ListQuery<Client>
}