package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity

import com.aticosoft.appointments.mobile.business.Application
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import dagger.MembersInjector
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.listener.InstanceLifecycleEvent
import javax.jdo.listener.LoadLifecycleListener

/**
 * Created by Rodrigo Quesada on 11/12/15.
 */
@Singleton
/*internal*/ class EntityInitializersManager @Inject protected constructor(
        private val entityInitializers: MutableSet<EntityInitializer<*>>
) {
    fun registerInitializers() {
        entityInitializers.forEach { it.register() }
    }
}

/*internal*/ class EntityInitializer<E : Entity> @Inject protected constructor(
        private val c: EntityInitializer.Context,
        private val entityType: Class<E>,
        private val entityInjector: MembersInjector<E>
) : LoadLifecycleListener {

    fun register() {
        c.persistenceContext.registerLifecycleListener(this, entityType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun postLoad(event: InstanceLifecycleEvent) {
        entityInjector.injectMembers(event.source as E)
    }

    class Context @Inject protected constructor(
            val persistenceContext: PersistenceContext,
            val application: Application<*, *>
    )
}