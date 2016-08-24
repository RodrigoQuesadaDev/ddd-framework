package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectObservationFilter
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Singleton
internal class TestDataParentQueries @Inject constructor(private val context: PersistenceContext) : Queries<TestDataParent> {

    fun valueIs(value: Int) = UniqueQuery(PersistableObjectObservationFilter(TestDataParent::class.java) { it.value == value }) {
        val d = QTestDataParent.testDataParent
        context.queryFactory.selectFrom(d).where(d.value.eq(value)).fetchOne()
    }

    fun isOdd(vararg filters: PersistableObjectObservationFilter<*>) = ListQuery(*filters) {
        val d = QTestDataParent.testDataParent
        context.queryFactory.selectFrom(d).where(d.value.mod(2).eq(1)).fetch()
    }

    fun isLessThan(max: Int, vararg filters: PersistableObjectObservationFilter<*>) = ListQuery(*filters) {
        val d = QTestDataParent.testDataParent
        context.queryFactory.selectFrom(d).where(d.value.lt(max)).fetch()
    }

    fun all() = ListQuery { context.queryFactory.selectFrom(QTestDataParent.testDataParent).fetch() }
}