package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.observer

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataListener
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestEntityListenersProviderForFiltering
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestEntityListenersProviderForQueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.observation.EntityListenersModule
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Rodrigo Quesada on 27/10/15.
 */
internal class TestEntityListenersModule : EntityListenersModule() {

    @Inject protected lateinit var s: Services

    override fun provideEntityListeners(entityListenersContainer: EntityListenersContainer): Array<EntityListener<*>> {
        return super.provideEntityListeners(entityListenersContainer) +
                s.testDataListenerProvider.get() +
                s.testEntityListenersProviderForFiltering.get() +
                s.testEntityListenersProviderForQueryViews.get()
    }

    class Services @Inject constructor(
            val testDataListenerProvider: Provider<TestDataListener>,
            val testEntityListenersProviderForFiltering: TestEntityListenersProviderForFiltering,
            val testEntityListenersProviderForQueryViews: TestEntityListenersProviderForQueryViews
    )
}