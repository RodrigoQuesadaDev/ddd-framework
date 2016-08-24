package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Repository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.dsl.SimpleExpression
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 21/09/15.
 */
/*internal*/ abstract class JdoRepository<P : PersistableObject<I>, I> protected constructor(entityPath: EntityPath<P>) : Repository<P, I> {

    private lateinit var m: InjectedMembers

    val queryEntity: QueryEntity<P, I> = QueryEntity(entityPath)

    override fun add(obj: P) {
        m.context.persistenceManager.makePersistent(obj)
    }

    override fun get(id: I) = m.context.queryFactory.selectFrom(queryEntity).where(queryEntity.id.eq(id)).fetchOne()

    override fun remove(obj: P) {
        m.context.persistenceManager.deletePersistent(obj)
    }

    override fun size() = m.context.queryFactory.selectFrom(queryEntity).fetchCount()

    //region Injection
    protected @Inject fun inject(injectedFields: InjectedMembers) {
        m = injectedFields
    }

    protected class InjectedMembers @Inject constructor(
            val context: PersistenceContext
    )
    //endregion
}

/*internal*/ class QueryEntity<P : PersistableObject<I>, I>(entityPath: EntityPath<P>) : EntityPath<P> by entityPath {
    private companion object {
        val ID_FIELD = "id"
    }

    @Suppress("UNCHECKED_CAST")
    val id: SimpleExpression<I> by lazy { entityPath.javaClass.getField(ID_FIELD).get(entityPath) as SimpleExpression<I> }
}
