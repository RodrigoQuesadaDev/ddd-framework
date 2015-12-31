package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 24/10/15.
 */
@Singleton
internal class TestDataRepository @Inject constructor(context: PersistenceContext) : JdoRepository<TestData>(context, QTestData.testData)