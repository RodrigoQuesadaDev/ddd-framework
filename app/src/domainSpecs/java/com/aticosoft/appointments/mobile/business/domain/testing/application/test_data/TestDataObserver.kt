package com.aticosoft.appointments.mobile.business.domain.testing.application.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 25/10/15.
 */
@Singleton
internal class TestDataObserver @Inject constructor(services: EntityObserver.Services, testDataRepository: TestDataRepository) : EntityObserver<TestData>(services, testDataRepository, TestData::class.java)