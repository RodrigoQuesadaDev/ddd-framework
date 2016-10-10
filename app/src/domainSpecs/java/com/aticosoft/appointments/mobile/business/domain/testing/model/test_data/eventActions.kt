package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventActionBase
import com.rodrigodev.common.properties.Delegates.atomicInteger

/**
 * Created by Rodrigo Quesada on 09/10/16.
 */
internal abstract class TestEventAction<E : Event> : EventActionBase<E>() {

    var executionPosition by atomicInteger()
        private set

    fun init(executionPosition: Int) {
        this.executionPosition = executionPosition
    }
}