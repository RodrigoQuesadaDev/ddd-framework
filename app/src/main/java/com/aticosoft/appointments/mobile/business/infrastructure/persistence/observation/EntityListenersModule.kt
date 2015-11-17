package com.aticosoft.appointments.mobile.business.infrastructure.persistence.observation

import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentListener
import com.aticosoft.appointments.mobile.business.domain.application.client.ClientListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 27/10/15.
 */
@Module
/*internal*/ open class EntityListenersModule {

    @Singleton
    protected class EntityListenersContainer @Inject constructor() {

        private val listenersList = arrayListOf<EntityListener<*>>();

        private var appointmentListener: AppointmentListener by ListDelegate(listenersList)
            @Inject protected set
        private var clientListener: ClientListener by ListDelegate(listenersList)
            @Inject protected set

        fun asArray() = listenersList.toTypedArray()
    }

    @Provides @Singleton @EntityListeners
    open fun provideEntityListeners(
            entityListenersContainer: EntityListenersContainer
    ): Array<EntityListener<*>> = entityListenersContainer.asArray()
}

internal annotation class EntityListeners

private class ListDelegate<T : Any>(private val list: MutableList<in T>) : ReadWriteProperty<Any, T> {

    protected lateinit var value: T

    override fun getValue(thisRef: Any, property: KProperty<*>): T = value

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
        list.add(value)
    }
}