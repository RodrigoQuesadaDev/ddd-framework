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
/*internal*/ class EntityInitializersManager @Inject protected constructor(
        @EntityInitializers private val entityInitializers: Array<EntityInitializer<*, *>>
) {
    fun registerInitializers() {
        entityInitializers.forEach { it.register() }
    }
}

/*internal*/ class EntityInitializer<E : Entity, C : ApplicationComponent> private constructor(
        @Provided private val c: EntityInitializer.Context,
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

    class Factory @Inject protected constructor(private val contextProvider: Provider<EntityInitializer.Context>) {

        fun <E : Entity, C : ApplicationComponent> create(entityType: Class<E>, injectCall: C.(E) -> Unit): EntityInitializer<E, C> {
            return EntityInitializer(contextProvider.get(), entityType, injectCall)
        }
    }
}

internal annotation class EntityInitializers