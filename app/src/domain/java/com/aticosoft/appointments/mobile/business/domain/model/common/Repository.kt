package com.aticosoft.appointments.mobile.business.domain.model.common

/**
 * Created by Rodrigo Quesada on 24/09/15.
 */
/*internal*/ interface Repository<E : Entity> {

    fun add(entity: E)

    fun get(id: Long): E?

    fun remove(entity: E)

    fun find(query: UniqueQuery<E>): E? = query.execute()

    fun find(query: ListQuery<E>): List<E> = query.execute()

    fun count(query: CountQuery<E>): Long = query.execute()

    fun size(): Long
}
