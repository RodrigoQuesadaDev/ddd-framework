package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 09/09/16.
 */
@Singleton
/*internal*/ abstract class JdoQueries<P : PersistableObject<*>> : Queries<P> {

    private lateinit var m: InjectedMembers

    protected val context: PersistenceContext
        get() = m.persistenceContext

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers) {
        m = injectedMembers
    }

    protected class InjectedMembers @Inject protected constructor(
            val persistenceContext: PersistenceContext
    )
    //endregion
}