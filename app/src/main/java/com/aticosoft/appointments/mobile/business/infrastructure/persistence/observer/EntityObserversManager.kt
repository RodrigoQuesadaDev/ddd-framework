package com.aticosoft.appointments.mobile.business.infrastructure.persistence.observer

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 16/10/15.
 */
@Singleton
internal class EntityObserversManager @Inject constructor(
        @EntityObservers private val entityObservers: Array<EntityObserver<*>>
) {

    fun registerObservers() {
        entityObservers.forEach { it.register() }
    }
}