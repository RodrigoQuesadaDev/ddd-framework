package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.observer

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataChildListener
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataListener
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataParentListener
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.observation.EntityListenersModule
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by Rodrigo Quesada on 27/10/15.
 */
internal class TestEntityListenersModule : EntityListenersModule() {

    @Inject protected lateinit var testDataListenerProvider: Provider<TestDataListener>
    @Inject protected lateinit var testDataParentListenerProvider: Provider<TestDataParentListener>
    @Inject protected lateinit var testDataChildListenerProvider: Provider<TestDataChildListener>

    override fun provideEntityListeners(entityListenersContainer: EntityListenersContainer): Array<EntityListener<*>> {
        return super.provideEntityListeners(entityListenersContainer) +
                testDataListenerProvider.get() +
                testDataParentListenerProvider.get() +
                testDataChildListenerProvider.get()
    }
}