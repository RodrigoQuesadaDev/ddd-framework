package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.SimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ProducedValue
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ValueProducer
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventActionBase
import com.rodrigodev.common.concurrent.inc
import com.rodrigodev.common.properties.Delegates.atomicInteger
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 09/10/16.
 */
internal abstract class TestEventAction<E : TestEvent> : EventActionBase<E>() {

    var executionPosition by atomicInteger()
        private set

    fun init(executionPosition: Int) {
        this.executionPosition = executionPosition
    }
}

internal abstract class TestSimpleEventAction<E : TestEvent, P : ValueProducer<E, V>, V : ProducedValue> : TestEventAction<E>(), SimpleEventAction<E> {

    private val lock = Any()
    private lateinit var m: InjectedMembers<E, P, V>

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
    protected fun inject(injectedMembers: InjectedMembers<E, P, V>) {
        m = injectedMembers
    }

    class InjectedMembers<E : TestEvent, P : ValueProducer<E, V>, V : ProducedValue> @Inject protected constructor(
            val valueProducer: P
    )
    //endregion

    //region Value Producer Classes
    @Singleton
    abstract class ValueProducer<E : TestEvent, out V : ProducedValue> protected constructor() {

        // Code wont't compile without this.
        private lateinit var eventType: Class<E>

        private val _producedValues = mutableListOf<V>()

        val producedValues: List<V>
            get() = _producedValues

        fun TestEventAction<E>.produce(value: Int) {
            _producedValues.add(producedValue(this, value))
        }

        protected abstract fun producedValue(eventAction: TestEventAction<*>, value: Int): V

        fun clear(): Unit = _producedValues.clear()
    }

    @Singleton
    internal class EmptyValueProducer<E : TestEvent> @Inject protected constructor() : TestSimpleEventAction.ValueProducer<E, ProducedValue>() {

        override fun producedValue(eventAction: TestEventAction<*>, value: Int) = object : ProducedValue {}
    }

    interface ProducedValue
    //endregion
}