package com.aticosoft.appointments.mobile.business.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.ListQuery

/**
 * Created by rodrigo on 27/09/15.
 */
internal interface ClientRepository : Repository<Client, ClientQueries>

/*internal*/ interface ClientQueries : Repository.Queries {

    fun nameLike(name: String): ListQuery<Client>
}