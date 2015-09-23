package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.QueryEntity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext

/**
 * Created by rodrigo on 21/09/15.
 */
/*internal*/ interface JdoRepository<E : Entity, Q : Repository.Queries> : Repository<E, Q> {

    val queryEntity: QueryEntity<E, *>
}

internal abstract class JdoRepositoryBase<E : Entity, Q : Repository.Queries>(protected val context: PersistenceContext) : JdoRepository<E, Q> {

    override abstract val queryEntity: QueryEntity<E, *>

    override fun add(entity: E) {
        context.persistenceManager.makePersistent(entity)
    }

    override fun get(id: Long) = context.queryFactory.selectFrom(queryEntity).where(queryEntity.id.eq(id)).fetchOne()

    override fun size() = context.queryFactory.selectFrom(queryEntity).fetchCount()
}