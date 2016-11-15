package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.SimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.TimesReceivedEvaluator
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ProducedValue
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ValueProducer
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventActionBase
import com.rodrigodev.common.collection.synchronized
import com.rodrigodev.common.concurrent.inc
import com.rodrigodev.common.properties.Delegates.atomicInteger
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 09/10/16.
 */
internal abstract class TestEventAction<E : TestEvent>(timesReceived: TimesReceivedEvaluator) : EventActionBase<E>(timesReceived) {

    var executionPosition by atomicInteger()
        private set

    fun init(executionPosition: Int) {
        this.executionPosition = executionPosition
    }
}

internal abstract class TestSimpleEventAction<E : TestEvent, A : TestEventAction<E>, P : ValueProducer<E, A, V>, V : ProducedValue>(
        timesReceived: TimesReceivedEvaluator
) : TestEventAction<E>(timesReceived), SimpleEventAction<E> {

    private val lock = Any()
    private lateinit var m: InjectedMembers<E, A, P, V>

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
    protected fun inject(injectedMembers: InjectedMembers<E, A, P, V>) {
        m = injectedMembers
    }

    class InjectedMembers<E : TestEvent, A : TestEventAction<E>, P : ValueProducer<E, A, V>, V : ProducedValue> @Inject protected constructor(
            val valueProducer: P
    )
    //endregion

    //region Value Producer Classes
    @Singleton
    abstract class ValueProducer<E : TestEvent, in A : TestEventAction<E>, out V : ProducedValue> protected constructor() {

        // Code won't compile without this.
        private lateinit var eventType: Class<E>

        private val _producedValues = mutableListOf<V>().synchronized()

        val producedValues: List<V>
            get() = _producedValues

        fun TestEventAction<E>.produce(value: Int) {
            @Suppress("UNCHECKED_CAST")
            _producedValues.add(producedValue(this as A, value))
        }

        protected abstract fun producedValue(eventAction: A, value: Int): V

        fun clear(): Unit = _producedValues.clear()
    }

    @Singleton
    internal class EmptyValueProducer<E : TestEvent> @Inject protected constructor() : TestSimpleEventAction.ValueProducer<E, TestEventAction<E>, ProducedValue>() {

        override fun producedValue(eventAction: TestEventAction<E>, value: Int) = object : ProducedValue {}
    }

    interface ProducedValue
    //endregion
}