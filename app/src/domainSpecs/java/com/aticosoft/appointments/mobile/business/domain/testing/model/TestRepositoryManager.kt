package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.JdoEntityRepository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext.PersistenceManagerFactoryAccessor
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 24/09/15.
 */
@Singleton
internal class TestRepositoryManager<E : Entity> @Inject protected constructor(
        private val context: PersistenceContext,
        private val repository: JdoEntityRepository<E>
) : PersistenceManagerFactoryAccessor {

    fun clear() {
        context.execute { context.queryFactory.delete(repository.queryEntity).execute() }
    }

    fun clearCache() {
        context.pmf.dataStoreCache.evictAll(true, repository.queryEntity.type)
    }
}
