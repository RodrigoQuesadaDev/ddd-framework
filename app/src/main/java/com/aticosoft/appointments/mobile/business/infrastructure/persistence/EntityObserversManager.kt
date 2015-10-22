package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.domain.application.appointment.AppointmentObserver
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 16/10/15.
 */
@Singleton
internal class EntityObserversManager @Inject constructor(
        private val persistenceContext: PersistenceContext,
        private val appointmentObserver: AppointmentObserver
) {

    fun registerObservers() {
        persistenceContext.registerEntityListeners(appointmentObserver.callbackListener)
    }
}