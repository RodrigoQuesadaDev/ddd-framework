package com.aticosoft.appointments.mobile.business.domain.testing.application.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 25/10/15.
 */
@Singleton
internal class TestDataObserver @Inject constructor(services: TestDataObserver.Services) : EntityObserver<TestData>(services, TestData::class.java) {

    protected class Services @Inject constructor(override val entityRepository: TestDataRepository) : EntityObserver.Services<TestData>()
}

@Singleton
internal class TestDataListener @Inject constructor(services: EntityListener.Services) : EntityListener<TestData>(services, TestData::class.java)