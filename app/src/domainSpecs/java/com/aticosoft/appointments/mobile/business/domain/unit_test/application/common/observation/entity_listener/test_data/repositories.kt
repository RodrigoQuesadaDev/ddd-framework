package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data

import com.aticosoft.appointments.mobile.business.domain.testing.model.TestRepositoryManager
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 14/11/15.
 */
@Singleton
internal class TestDataParentRepository @Inject constructor() : JdoRepository<TestDataParent>(QTestDataParent.testDataParent)

@Singleton
internal class TestDataParentRepositoryManager @Inject constructor(services: TestRepositoryManager.Services, repository: TestDataParentRepository) : TestRepositoryManager<TestDataParentRepository>(services, repository)