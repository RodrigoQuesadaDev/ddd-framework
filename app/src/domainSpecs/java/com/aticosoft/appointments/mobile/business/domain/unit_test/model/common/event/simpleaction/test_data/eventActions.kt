package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.SimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEventAction
import com.rodrigodev.common.concurrent.inc
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
internal abstract class LocalTestEventAction<E : TestEvent> : TestEventAction<E>(), SimpleEventAction<E> {

    private val lock = Any()
    private lateinit var m: InjectedMembers<E>

    private var updateEventTimes: Int = 0
    private val eventUpdateCountMap = mutableMapOf<Long, AtomicInteger>()

    fun updateEvent(times: Int): Unit = synchronized(lock) {
        updateEventTimes = times
    }

    override fun execute(event: E) = synchronized(lock) {
        var updateCount = eventUpdateCountMap[event.id]
        if (updateCount == null) updateCount = eventUpdateCountMap.getOrPut(event.id, { AtomicInteger() })
        if (updateCount.get() < updateEventTimes) {
            ++event.value
            ++updateCount
        }
        with(m.valueProducer) { produce(event.value) }
    }

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

        fun LocalTestEventAction<E>.produce(value: Int) {
            _producedValues.add(ProducedValue(executionPosition + 1, value))
        }

        fun clear(): Unit = _producedValues.clear()
    }

    data class ProducedValue(val actionExecutionPosition: Int, val value: Int) {

        override fun toString() = "a$actionExecutionPosition:$value"
    }
    //endregion
}

//region ThreeSubscriptions event's actions
@Singleton
internal class ThreeSubscriptionsEventAction1 @Inject constructor() : LocalTestEventAction<ThreeSubscriptionsEvent>()

@Singleton
internal class ThreeSubscriptionsEventAction2 @Inject constructor() : LocalTestEventAction<ThreeSubscriptionsEvent>()

@Singleton
internal class ThreeSubscriptionsEventAction3 @Inject constructor() : LocalTestEventAction<ThreeSubscriptionsEvent>()
//endregion

//region FiveSubscriptions event's actions
@Singleton
internal class FiveSubscriptionsEventAction1 @Inject constructor() : LocalTestEventAction<FiveSubscriptionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction2 @Inject constructor() : LocalTestEventAction<FiveSubscriptionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction3 @Inject constructor() : LocalTestEventAction<FiveSubscriptionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction4 @Inject constructor() : LocalTestEventAction<FiveSubscriptionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction5 @Inject constructor() : LocalTestEventAction<FiveSubscriptionsEvent>()
//endregion