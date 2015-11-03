package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.dsl.NumberPath

/**
 * Created by Rodrigo Quesada on 21/09/15.
 */
/*internal*/ interface JdoRepository<E : Entity> : Repository<E> {

    val queryEntity: QueryEntity<E>
}

/*internal*/ abstract class JdoRepositoryBase<E : Entity>(protected val context: PersistenceContext, entityPath: EntityPath<E>) : JdoRepository<E> {

    override val queryEntity = QueryEntity(entityPath)

    override fun add(entity: E) {
        context.persistenceManager.makePersistent(entity)
    }

    override fun get(id: Long) = context.queryFactory.selectFrom(queryEntity).where(queryEntity.id.eq(id)).fetchOne()

    override fun remove(entity: E) {
        context.persistenceManager.deletePersistent(entity)
    }

    //TODO test this method works after changing TestDataParent hierarchy on jdo/orm files?
    override fun size() = context.queryFactory.selectFrom(queryEntity).fetchCount()
}

/*internal*/ class QueryEntity<E : Entity>(entityPath: EntityPath<E>) : EntityPath<E> by entityPath {
    private companion object {
        val ID_FIELD = "id"
    }

    @Suppress("UNCHECKED_CAST")
    val id: NumberPath<Long> = entityPath.javaClass.getField(ID_FIELD).get(entityPath) as NumberPath<Long>
}