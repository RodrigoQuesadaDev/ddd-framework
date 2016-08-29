package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoRepository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext.PersistenceManagerFactoryAccessor
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 24/09/15.
 */
internal abstract class TestRepositoryManager<P : PersistableObject<I>, I, R : JdoRepository<P, I>> protected constructor() : PersistenceManagerFactoryAccessor {

    private lateinit var m: InjectedMembers<P, I, R>

    fun clear() = with(m) {
        context.execute { context.queryFactory.delete(repository.queryEntity).execute() }
    }

    fun clearCache() = with(m) {
        context.pmf.dataStoreCache.evictAll(true, repository.queryEntity.type)
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<P, I, R>) {
        m = injectedMembers
    }

    protected class InjectedMembers<P : PersistableObject<I>, I, R : JdoRepository<P, I>> @Inject protected constructor(
            val context: PersistenceContext,
            val repository: R
    )
    //endregion
}
