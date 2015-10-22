package com.aticosoft.appointments.mobile.business.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.model.common.queries.ListQuery

/**
 * Created by rodrigo on 19/10/15.
 */
interface ClientQueries {

    fun nameLike(name: String): ListQuery<Client>
}