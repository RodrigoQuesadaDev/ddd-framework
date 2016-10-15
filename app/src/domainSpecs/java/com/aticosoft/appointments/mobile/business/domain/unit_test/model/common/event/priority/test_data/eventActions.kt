package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.test_data

import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ProducedValue
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ValueProducer
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
internal abstract class LocalTestEventAction<E : TestEvent> : TestSimpleEventAction<E, LocalValueProducer<E>, LocalProducedValue>()

//region Value Producer Classes
@Singleton
internal class LocalValueProducer<E : TestEvent> @Inject protected constructor() : ValueProducer<E, LocalProducedValue>() {

    override fun producedValue(eventAction: TestEventAction<*>, value: kotlin.Int)
            = LocalProducedValue(eventAction.priority, value)
}

internal data class LocalProducedValue(val priority: Int, val value: Int) : ProducedValue {

    override fun toString() = "a$priority:$value"
}
//endregion

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