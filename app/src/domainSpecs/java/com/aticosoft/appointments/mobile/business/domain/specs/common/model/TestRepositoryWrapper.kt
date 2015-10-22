package com.aticosoft.appointments.mobile.business.domain.specs.common.model

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.TestRepository.Services
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 24/09/15.
 */
internal class TestRepository<R : JdoRepository<E>, E : Entity>(
        private val s: Services,
        private val repository: R
) {

    fun clear() {
        s.transactionManager.transactional { s.context.queryFactory.delete(repository.queryEntity).execute() }
    }

    protected class Services @Inject constructor(
            val transactionManager: TransactionManager,
            val context: PersistenceContext
    )

    @Singleton
    class Factory @Inject constructor(val services: Services) {

        fun <R : JdoRepository<E>, E : Entity> create(repository: R) = TestRepository<R, E>(services, repository)
    }
}