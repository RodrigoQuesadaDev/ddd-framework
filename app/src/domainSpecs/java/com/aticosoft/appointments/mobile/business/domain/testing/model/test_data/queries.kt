package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.EntityQueries
import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
@Singleton
internal class TestDataQueries @Inject constructor(private val context: PersistenceContext) : EntityQueries<TestData> {

    fun valueIs(value: Int) = UniqueQuery {
        val d = QTestData.testData
        context.queryFactory.selectFrom(d).where(d.value.eq(value)).fetchOne()
    }
}

internal enum class TestDataQueryView : QueryView {
    ;

    override lateinit var _filterTypes: Sequence<Class<out Entity>>
    override lateinit var fetchGroupName: String
}