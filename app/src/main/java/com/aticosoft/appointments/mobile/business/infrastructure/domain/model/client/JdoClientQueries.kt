package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.model.client.QClient
import com.aticosoft.appointments.mobile.business.domain.model.common.ListQuery
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
@Singleton
/*internal*/ class JdoClientQueries @Inject constructor(private val context: PersistenceContext) : ClientQueries {

    override fun nameLike(name: String) = ListQuery {
        val c = QClient.client
        context.queryFactory.selectFrom(c).where(c.name.eq(name)).fetch()
    }
}