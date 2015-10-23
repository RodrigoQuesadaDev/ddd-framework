package com.aticosoft.appointments.mobile.business.domain.model.common

import com.aticosoft.appointments.mobile.business.domain.model.common.queries.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.UniqueQuery

/**
 * Created by rodrigo on 24/09/15.
 */
/*internal*/ interface Repository<E : Entity> {

    fun add(entity: E)

    fun get(id: Long): E?

    fun remove(entity: E)

    fun find(query: UniqueQuery<E>): E? = query.execute()

    fun find(query: ListQuery<E>): List<E> = query.execute()

    fun size(): Long
}
