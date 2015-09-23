package com.aticosoft.appointments.mobile.business.domain.specs.common.model

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.queries.UniqueQuery
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.TestRepositoryWrapper.Services
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 24/09/15.
 */
internal class TestRepositoryWrapper<R : JdoRepository<E, Q>, E : Entity, Q : Repository.Queries>(
        private val s: Services,
        private val repository: R
) : JdoRepository<E, Q> by repository {

    override fun add(entity: E) {
        s.transactionManager.transactional { repository.add(entity) }
    }

    override fun get(id: Long) = s.transactionManager.transactional { repository.get(id) }

    override fun find(query: UniqueQuery<E?>) = s.transactionManager.transactional { repository.find(query) }

    override fun find(query: ListQuery<E>) = s.transactionManager.transactional { repository.find(query) }

    override fun size() = s.transactionManager.transactional { repository.size() }

    fun clear() {
        s.transactionManager.transactional { s.context.queryFactory.delete(queryEntity).execute() }
    }

    protected class Services @Inject constructor(
            val transactionManager: TransactionManager,
            val context: PersistenceContext
    )

    @Singleton
    class Factory @Inject constructor(val services: Services) {

        fun <R : JdoRepository<E, Q>, E : Entity, Q : Repository.Queries> create(repository: R) = TestRepositoryWrapper<R, E, Q>(services, repository)
    }
}