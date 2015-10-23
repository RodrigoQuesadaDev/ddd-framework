package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.testing.model.TestRepositoryManager.Services
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.client.JdoClientRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment.JdoAppointmentRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 24/09/15.
 */
internal abstract class TestRepositoryManager<R : JdoRepository<*>>(
        private val s: Services,
        private val repository: R
) {

    fun clear() {
        s.transactionManager.transactional { s.context.queryFactory.delete(repository.queryEntity).execute() }
    }

    class Services @Inject constructor(
            val transactionManager: TransactionManager,
            val context: PersistenceContext
    )
}

@Singleton
internal class ClientRepositoryManager @Inject constructor(services: Services, repository: JdoClientRepository) : TestRepositoryManager<JdoClientRepository>(services, repository)

@Singleton
internal class AppointmentRepositoryManager @Inject constructor(services: Services, repository: JdoAppointmentRepository) : TestRepositoryManager<JdoAppointmentRepository>(services, repository)

@Singleton
internal class TestDataRepositoryManager @Inject constructor(services: Services, repository: TestDataRepository) : TestRepositoryManager<TestDataRepository>(services, repository)