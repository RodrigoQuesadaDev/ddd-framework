package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.SimpleEventAction
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventActionBase
import com.rodrigodev.common.properties.delegates.AtomicBooleanDelegate
import com.rodrigodev.common.properties.delegates.AtomicIntegerDelegate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
@Singleton
internal class TestEventAction<E : Event> @Inject constructor() : EventActionBase<E>(), SimpleEventAction<E> {

    private var executionPosition by AtomicIntegerDelegate()

    var wasTriggered by AtomicBooleanDelegate()
        private set

    private var updateEvent by AtomicBooleanDelegate()

    fun init(executionPosition: Int) {
        this.executionPosition = executionPosition
    }

    fun updateEvent(value: Boolean) {
        updateEvent = value
    }

    override fun execute(event: E) {
        wasTriggered = true
    }

    //region Value Producer Classes
    @Singleton
    class ValueProducer<E : Event> @Inject protected constructor(
            // Code wont't compile without this.
            eventType: Class<E>
    ) {

        private val _producedValues = mutableListOf<ProducedValue>()

        val producedValues: List<ProducedValue>
            get() = _producedValues

        fun produce(action: TestEventAction<E>, value: Int) {
            _producedValues.add(ProducedValue(action.executionPosition, value))
        }
    }

    data class ProducedValue(val actionExecutionPosition: Int, val value: Int)
    //endregion
}

//region NoSubsEvent's actions

//endregion