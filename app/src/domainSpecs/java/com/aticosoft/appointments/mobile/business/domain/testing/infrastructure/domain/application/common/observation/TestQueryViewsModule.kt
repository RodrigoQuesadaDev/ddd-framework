package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentQueryView
import com.aticosoft.appointments.mobile.business.infrastructure.domain.application.common.observation.QueryViewsModule

/**
 * Created by Rodrigo Quesada on 18/11/15.
 */
internal class TestQueryViewsModule : QueryViewsModule() {

    override fun provideQueryViews(): Array<Class<out Enum<*>>> = arrayOf(
            TestDataParentQueryView::class.java,
            com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParentQueryView::class.java
    )
}