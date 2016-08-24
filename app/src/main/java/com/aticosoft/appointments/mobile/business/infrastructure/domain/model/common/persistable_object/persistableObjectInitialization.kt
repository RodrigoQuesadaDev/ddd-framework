package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object

import com.aticosoft.appointments.mobile.business.Application
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
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
/*internal*/ class PersistableObjectInitializersManager @Inject protected constructor(
        private val persistableObjectInitializers: MutableSet<PersistableObjectInitializer<*>>
) {
    fun registerInitializers() {
        persistableObjectInitializers.forEach { it.register() }
    }
}

/*internal*/ class PersistableObjectInitializer<P : PersistableObject<*>> @Inject protected constructor(
        private val c: Context,
        private val objectType: Class<P>,
        private val objectInjector: MembersInjector<P>
) : LoadLifecycleListener {

    fun register() {
        c.persistenceContext.registerLifecycleListener(this, objectType)
    }

    @Suppress("UNCHECKED_CAST")
    override fun postLoad(event: InstanceLifecycleEvent) {
        objectInjector.injectMembers(event.source as P)
    }

    class Context @Inject protected constructor(
            val persistenceContext: PersistenceContext,
            val application: Application<*, *>
    )
}