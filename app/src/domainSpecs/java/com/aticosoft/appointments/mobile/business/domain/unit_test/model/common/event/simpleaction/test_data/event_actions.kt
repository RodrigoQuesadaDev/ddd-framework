package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.SimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventActionBase
import com.rodrigodev.common.properties.delegates.AtomicBooleanDelegate
import com.rodrigodev.common.properties.delegates.AtomicIntegerDelegate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
internal abstract class TestEventAction<E : TestEvent> : EventActionBase<E>(), SimpleEventAction<E> {

    private lateinit var m: InjectedMembers<E>

    private var executionPosition by AtomicIntegerDelegate()

    private var updateEvent by AtomicBooleanDelegate()

    fun init(executionPosition: Int) {
        this.executionPosition = executionPosition
    }

    fun updateEvent(value: Boolean) {
        updateEvent = value
    }

    override fun execute(event: E) = with(m.valueProducer) { produce(event.value) }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<E>) {
        m = injectedMembers
    }

    class InjectedMembers<E : TestEvent> @Inject protected constructor(
            val valueProducer: ValueProducer<E>
    )
    //endregion

    //region Value Producer Classes
    @Singleton
    class ValueProducer<E : TestEvent> @Inject protected constructor(
            // Code wont't compile without this.
            eventType: Class<E>
    ) {

        private val _producedValues = mutableListOf<ProducedValue>()

        val producedValues: List<ProducedValue>
            get() = _producedValues

        fun TestEventAction<E>.produce(value: Int) {
            _producedValues.add(ProducedValue(executionPosition, value))
        }
    }

    data class ProducedValue(val actionExecutionPosition: Int, val value: Int)
    //endregion
}

//region ThreeSubscriptions event's actions
@Singleton
internal class ThreeSubscriptionsEventAction1 @Inject constructor() : TestEventAction<ThreeSubscriptionsEvent>()

@Singleton
internal class ThreeSubscriptionsEventAction2 @Inject constructor() : TestEventAction<ThreeSubscriptionsEvent>()

@Singleton
internal class ThreeSubscriptionsEventAction3 @Inject constructor() : TestEventAction<ThreeSubscriptionsEvent>()
//endregion

//region FiveSubscriptions event's actions
@Singleton
internal class FiveSubscriptionsEventAction1 @Inject constructor() : TestEventAction<FiveSubscriptionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction2 @Inject constructor() : TestEventAction<FiveSubscriptionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction3 @Inject constructor() : TestEventAction<FiveSubscriptionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction4 @Inject constructor() : TestEventAction<FiveSubscriptionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction5 @Inject constructor() : TestEventAction<FiveSubscriptionsEvent>()
//endregion