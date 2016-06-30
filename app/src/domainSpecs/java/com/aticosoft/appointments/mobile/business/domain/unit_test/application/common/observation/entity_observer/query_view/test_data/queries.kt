package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter
import com.aticosoft.appointments.mobile.business.domain.model.common.EntityQueries
import com.aticosoft.appointments.mobile.business.domain.model.common.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Singleton
internal class TestDataParentQueries @Inject constructor(private val context: PersistenceContext) : EntityQueries<TestDataParent> {

    fun valueIs(value: Int) = UniqueQuery(EntityObservationFilter(TestDataParent::class.java) { it.value == value }) {
        val d = QTestDataParent.testDataParent
        context.queryFactory.selectFrom(d).where(d.value.eq(value)).fetchOne()
    }

    fun isOdd(vararg filters: EntityObservationFilter<*>) = ListQuery(*filters) {
        val d = QTestDataParent.testDataParent
        context.queryFactory.selectFrom(d).where(d.value.mod(2).eq(1)).fetch()
    }

    fun isLessThan(max: Int, vararg filters: EntityObservationFilter<*>) = ListQuery(*filters) {
        val d = QTestDataParent.testDataParent
        context.queryFactory.selectFrom(d).where(d.value.lt(max)).fetch()
    }

    fun all() = ListQuery { context.queryFactory.selectFrom(QTestDataParent.testDataParent).fetch() }
}