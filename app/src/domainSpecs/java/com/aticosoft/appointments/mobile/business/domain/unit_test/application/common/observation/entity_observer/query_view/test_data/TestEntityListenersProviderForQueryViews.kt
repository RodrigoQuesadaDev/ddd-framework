package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/11/15.
 */
@Singleton
internal class TestEntityListenersProviderForQueryViews @Inject constructor(
        val testDataParentListenerProvider: Provider<TestDataParentListener>,
        val testDataChildListenerProvider: Provider<TestDataChildListener>,
        val testDataGrandChildListenerProvider: Provider<TestDataGrandChildListener>
) {
    fun get(): Array<EntityListener<*>> = arrayOf(testDataParentListenerProvider.get(), testDataChildListenerProvider.get(), testDataGrandChildListenerProvider.get())
}