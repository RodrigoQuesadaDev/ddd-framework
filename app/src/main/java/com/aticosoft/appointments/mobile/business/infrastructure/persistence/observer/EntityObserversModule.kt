package com.aticosoft.appointments.mobile.business.infrastructure.persistence.observer

import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentObserver
import com.aticosoft.appointments.mobile.business.domain.application.client.ClientObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by rodrigo on 27/10/15.
 */
@Module
/*internal*/ open class EntityObserversModule {

    @Singleton
    protected class EntityObserversContainer @Inject constructor() {

        private val observersList = arrayListOf<EntityObserver<*>>();

        //TODO uncomment visibility modifiers when KT-9712/KT-5870 is resolved
        /*private*/ var appointmentObserver: AppointmentObserver by ListDelegate(observersList)
            @Inject protected  set
        /*private*/ var clientObserver: ClientObserver by ListDelegate(observersList)
            @Inject protected set

        fun asArray() = observersList.toTypedArray()

        class ListDelegate<T : Any>(private val list: MutableList<in T>) : ReadWriteProperty<Any, T> {

            protected lateinit var value: T

            override fun getValue(thisRef: Any, property: KProperty<*>): T = value

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
                this.value = value
                list.add(value)
            }
        }
    }

    @Provides @Singleton @EntityObservers
    open fun provideEntityObservers(
            entityObserversContainer: EntityObserversContainer
    ): Array<EntityObserver<*>> = entityObserversContainer.asArray()
}

internal annotation class EntityObservers