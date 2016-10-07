package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.valueobject

import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.exceptions.ValueObjectModificationException
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.ValueObjects
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.listener.DirtyLifecycleListener
import javax.jdo.listener.InstanceLifecycleEvent

/**
 * Created by Rodrigo Quesada on 07/10/16.
 */
@Singleton
/*internal*/ class ValueObjectsManager @Inject protected constructor(
        @ValueObjects private val valueObjects: MutableSet<Class<*>>,
        private val context: PersistenceContext
) {

    val registeredValueObjects: List<Class<*>> = valueObjects.toList()

    fun registerValueObjects(): Unit = context.registerLifecycleListener(ValueObjectLifecycleListener(), *valueObjects.toTypedArray())

    private class ValueObjectLifecycleListener : DirtyLifecycleListener {

        override fun preDirty(event: InstanceLifecycleEvent) = throw ValueObjectModificationException(event.source)

        //region Non-Implemented Methods
        override fun postDirty(event: InstanceLifecycleEvent?) {
            // Do nothing!
        }
        //endregion
    }
}