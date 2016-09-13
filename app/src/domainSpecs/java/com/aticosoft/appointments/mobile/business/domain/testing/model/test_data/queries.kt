package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoQueries
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
@Singleton
internal class TestDataQueries @Inject constructor() : JdoQueries<TestData>() {

    fun valueIs(value: Int) = UniqueQuery {
        val d = QTestData.testData
        context.queryFactory.selectFrom(d).where(d.value.eq(value)).fetchOne()
    }
}

internal enum class TestDataQueryView : QueryView {
    ;

    override lateinit var _filterTypes: Sequence<Class<out PersistableObject<*>>>
    override lateinit var fetchGroupName: String
}