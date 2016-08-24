package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object

/**
 * Created by Rodrigo Quesada on 24/09/15.
 */
/*internal*/ interface Repository<P : PersistableObject<I>, I> {

    fun add(obj: P)

    fun get(id: I): P?

    fun remove(obj: P)

    fun find(query: UniqueQuery<P>): P? = query.execute()

    fun find(query: ListQuery<P>): List<P> = query.execute()

    fun count(query: CountQuery<P>): Long = query.execute()

    fun size(): Long
}
