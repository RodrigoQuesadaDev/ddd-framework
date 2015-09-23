package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.model.client.Client
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientQueries
import com.aticosoft.appointments.mobile.business.domain.model.client.ClientRepository
import com.aticosoft.appointments.mobile.business.domain.model.client.QClient
import com.aticosoft.appointments.mobile.business.domain.model.common.QueryEntity
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.ListQuery
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepositoryBase
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 27/09/15.
 */
@Singleton
/*internal*/ class JdoClientRepository @Inject constructor(context: PersistenceContext) : JdoRepositoryBase<Client, ClientQueries>(context), ClientRepository {

    override val QUERIES = JdoClientQueries(context)

    override val queryEntity = object : QueryEntity<Client, QClient>(QClient.client) {
        override val id = entityPath.id
    }
}

internal class JdoClientQueries(val context: PersistenceContext) : ClientQueries {

    override fun nameLike(name: String) = ListQuery {
        val c = QClient.client
        context.queryFactory.selectFrom(c).where(c.name.eq(name)).fetch()
    }
}