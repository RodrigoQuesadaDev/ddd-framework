package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity

import com.aticosoft.appointments.mobile.business.Application
import com.aticosoft.appointments.mobile.business.ApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.google.auto.factory.Provided
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import javax.jdo.listener.InstanceLifecycleEvent
import javax.jdo.listener.LoadLifecycleListener

/**
 * Created by Rodrigo Quesada on 11/12/15.
 */
@Singleton
/*internal*/ class EntityInjectorsManager @Inject protected constructor(
        @EntityInjectors private val entityInjectors: Array<EntityInjector<*, *>>
) {
    fun registerInjectors() {
        entityInjectors.forEach { it.register() }
    }
}

/*internal*/ class EntityInjector<E : Entity, C : ApplicationComponent> private constructor(
        @Provided private val c: EntityInjector.Context,
        private val entityType: Class<E>,
        private val injectCall: C.(E) -> Unit
) : LoadLifecycleListener {

    fun register() {
        c.persistenceContext.registerLifecycleListener(this, entityType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun postLoad(event: InstanceLifecycleEvent) {
        (c.application.component as C).injectCall(event.source as E)
    }

    class Context @Inject protected constructor(
            val persistenceContext: PersistenceContext,
            val application: Application<*>
    )

    class Factory @Inject protected constructor(private val contextProvider: Provider<EntityInjector.Context>) {

        fun <E : Entity, C : ApplicationComponent> create(entityType: Class<E>, injectCall: C.(E) -> Unit): EntityInjector<E, C> {
            return EntityInjector(contextProvider.get(), entityType, injectCall)
        }
    }
}

internal annotation class EntityInjectors