package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.UniqueQuery
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoQueries
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.dsl.SimpleExpression
import com.rodrigodev.common.querydsl.entityPathFor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 06/11/16.
 */
@Singleton
internal class LocalTestDataQueries<E : AbstractTestData> @Inject constructor(val entityType: Class<E>) : JdoQueries<E>() {

    private val d = LocalQueryEntity(entityPathFor(entityType))

    fun valueIs(value: Int) = UniqueQuery {
        context.queryFactory.selectFrom(d).where(d.value.eq(value)).fetchOne()
    }

    fun all() = ListQuery {
        context.queryFactory.selectFrom(d).fetch()
    }

    //region Other Classes
    class LocalQueryEntity<E : AbstractTestData>(entityPath: EntityPath<E>) : EntityPath<E> by entityPath {
        private companion object {
            val VALUE_FIELD = "value"
        }

        @Suppress("UNCHECKED_CAST")
        val value: SimpleExpression<Int> = entityPath.javaClass.getField(VALUE_FIELD).get(entityPath) as SimpleExpression<Int>
    }
    //endregion
}