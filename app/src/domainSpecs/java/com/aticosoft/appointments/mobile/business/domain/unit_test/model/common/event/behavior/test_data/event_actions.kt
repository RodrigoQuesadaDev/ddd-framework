package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.behavior.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventActionBase
import com.rodrigodev.common.properties.delegates.AtomicBooleanDelegate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
//region OneSubscriptionEvent
@Singleton
internal class TestEventAction<E : Event> @Inject constructor(val value: Int) : EventActionBase<E>() {

    var wasTriggered by AtomicBooleanDelegate()
        private set

    private var updateEvent by AtomicBooleanDelegate()

    fun updateEvent(value: Boolean) {
        updateEvent = value
    }
}
//endregion