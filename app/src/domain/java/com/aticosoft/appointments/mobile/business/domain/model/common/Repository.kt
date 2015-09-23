package com.aticosoft.appointments.mobile.business.domain.model.common

import com.aticosoft.appointments.mobile.business.domain.model.common.Repository.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.UniqueQuery

/**
 * Created by rodrigo on 24/09/15.
 */
/*internal*/ interface Repository<E : Entity, Q : Queries> {

    val QUERIES: Q

    fun add(entity: E): Unit

    fun get(id: Long): E?

    fun find(query: UniqueQuery<E?>) = query.execute()

    fun find(query: ListQuery<E>) = query.execute()

    fun size(): Long

    //TODO should be protected?
    interface Queries
}
