package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.SimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEventAction
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
internal abstract class LocalTestEventAction<E : TestEvent> : TestEventAction<E>(), SimpleEventAction<E> {

    private val lock = Any()
    private lateinit var m: InjectedMembers<E>

    override fun execute(event: E) = synchronized(lock) {
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
            _producedValues.add(ProducedValue(priority, value))
        }

        fun clear(): Unit = _producedValues.clear()
    }

    data class ProducedValue(val priority: Int, val value: Int) {

        override fun toString() = "a$priority:$value"
    }
    //endregion
}

//region SamePriority event's actions
@Singleton
internal class SamePriorityEventAction1 @Inject constructor() : LocalTestEventAction<SamePriorityEvent>() {
    override val priority = 100
}

@Singleton
internal class SamePriorityEventAction2 @Inject constructor() : LocalTestEventAction<SamePriorityEvent>() {
    override val priority = 100
}

@Singleton
internal class SamePriorityEventAction3 @Inject constructor() : LocalTestEventAction<SamePriorityEvent>() {
    override val priority = 100
}

@Singleton
internal class SamePriorityEventAction4 @Inject constructor() : LocalTestEventAction<SamePriorityEvent>() {
    override val priority = -70
}

@Singleton
internal class SamePriorityEventAction5 @Inject constructor() : LocalTestEventAction<SamePriorityEvent>() {
    override val priority = -70
}

@Singleton
internal class SamePriorityEventAction6 @Inject constructor() : LocalTestEventAction<SamePriorityEvent>() {
    override val priority = -70
}
//endregion

//region DifferentPriority event's actions
@Singleton
internal class DifferentPriorityEventAction1 @Inject constructor() : LocalTestEventAction<DifferentPriorityEvent>() {
    override val priority = 900
}

@Singleton
internal class DifferentPriorityEventAction2 @Inject constructor() : LocalTestEventAction<DifferentPriorityEvent>() {
    override val priority = 100
}

@Singleton
internal class DifferentPriorityEventAction3 @Inject constructor() : LocalTestEventAction<DifferentPriorityEvent>() {
    override val priority = 10
}

@Singleton
internal class DifferentPriorityEventAction4 @Inject constructor() : LocalTestEventAction<DifferentPriorityEvent>() {
    override val priority = -30
}

@Singleton
internal class DifferentPriorityEventAction5 @Inject constructor() : LocalTestEventAction<DifferentPriorityEvent>() {
    override val priority = -500
}
//endregion

//region DefaultPriority event's actions
@Singleton
internal class DefaultPriorityEventAction1 @Inject constructor() : LocalTestEventAction<DefaultPriorityEvent>() {
    override val priority = 1
}

@Singleton
internal class DefaultPriorityEventAction2 @Inject constructor() : LocalTestEventAction<DefaultPriorityEvent>()

@Singleton
internal class DefaultPriorityEventAction3 @Inject constructor() : LocalTestEventAction<DefaultPriorityEvent>() {
    override val priority = -1
}
//endregion