package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter
import com.aticosoft.appointments.mobile.business.domain.model.common.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Singleton
internal class TestDataParentQueries @Inject constructor(private val context: PersistenceContext) {
    private companion object {
        //TODO remove when https://github.com/querydsl/querydsl/issues/1665 is fixed
        val ODD_NUMBERS_UP_TO_100 = (1..99 step 2).toArrayList()
    }

    fun valueIs(value: Int) = UniqueQuery(EntityObservationFilter(TestDataParent::class.java) { it.value == value }) {
        val d = QTestDataParent.testDataParent
        context.queryFactory.selectFrom(d).where(d.value.eq(value)).fetchOne()
    }

    fun isOdd(vararg filters: EntityObservationFilter<*>) = ListQuery(*filters) {
        val d = QTestDataParent.testDataParent
        //TODO replace with commented code when https://github.com/querydsl/querydsl/issues/1665 is fixed
        context.queryFactory.selectFrom(d).where(d.value.`in`(ODD_NUMBERS_UP_TO_100)).fetch()
        //context.queryFactory.selectFrom(d).where(d.value.mod(2).eq(1)).fetch()
    }

    fun all() = ListQuery { context.queryFactory.selectFrom(QTestDataParent.testDataParent).fetch() }
}