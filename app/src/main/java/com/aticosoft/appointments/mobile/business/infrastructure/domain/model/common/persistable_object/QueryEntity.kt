@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.dsl.NumberExpression
import com.querydsl.core.types.dsl.SimpleExpression
import com.rodrigodev.common.querydsl.entityPathFor

/**
 * Created by Rodrigo Quesada on 13/09/16.
 */
/*internal*/ abstract class QueryEntityBase<P : PersistableObject<I>, I, out X1 : SimpleExpression<I>>(entityPath: EntityPath<P>) : EntityPath<P> by entityPath {
    private companion object {
        val ID_FIELD = "id"
    }

    @Suppress("UNCHECKED_CAST")
    val id: X1 = entityPath.javaClass.getField(ID_FIELD).get(entityPath) as X1
}

/*internal*/ open class QueryEntity<P : PersistableObject<I>, I>(entityPath: EntityPath<P>) : QueryEntityBase<P, I, SimpleExpression<I>>(entityPath)

/*internal*/ class QueryEntityForEvent<E : Event>(entityPath: EntityPath<E>) : QueryEntityBase<E, Long, NumberExpression<Long>>(entityPath)

//region Utils
internal inline fun <P : PersistableObject<*>> Class<P>.entityPath() = entityPathFor(this)
//endregion