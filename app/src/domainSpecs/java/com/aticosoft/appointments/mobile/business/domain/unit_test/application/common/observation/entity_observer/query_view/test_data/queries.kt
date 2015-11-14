package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Singleton
internal class TestDataParentQueries @Inject constructor(private val context: PersistenceContext) {

    fun valueIs(value: Int) = UniqueQuery {
        val d = QTestDataParent.testDataParent
        context.queryFactory.selectFrom(d).where(d.value.eq(value)).fetchOne()
    }
}