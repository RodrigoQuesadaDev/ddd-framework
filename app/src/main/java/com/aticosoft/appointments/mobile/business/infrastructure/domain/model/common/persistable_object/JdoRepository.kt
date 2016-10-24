@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Repository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.properties.Delegates.postInitialized
import com.rodrigodev.common.properties.UnsafePostInitialized
import com.rodrigodev.common.properties.delegates.UnsafePostInitializedPropertyDelegate.UnsafePropertyInitializer
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 21/09/15.
 */
/*internal*/ abstract class JdoRepository<P : PersistableObject<I>, I> protected constructor() : Repository<P, I>, UnsafePostInitialized {

    private lateinit var m: InjectedMembers<P>
    override val _propertyInitializer = UnsafePropertyInitializer()

    val queryEntity by postInitialized { QueryEntity(m.objectType.entityPath()) }

    override fun add(obj: P) {
        m.context.persistenceManager.makePersistent(obj)
    }

    override fun get(id: I) = m.context.queryFactory.selectFrom(queryEntity).where(queryEntity.id.eq(id)).fetchOne()

    override fun remove(obj: P) {
        m.context.persistenceManager.deletePersistent(obj)
    }

    override fun size() = m.context.queryFactory.selectFrom(queryEntity).fetchCount()

    //region Injection
    protected @Inject fun inject(injectedFields: InjectedMembers<P>) {
        m = injectedFields
        _init()
    }

    protected class InjectedMembers<P : PersistableObject<*>> @Inject constructor(
            val context: PersistenceContext,
            val objectType: Class<P>
    )
    //endregion
}