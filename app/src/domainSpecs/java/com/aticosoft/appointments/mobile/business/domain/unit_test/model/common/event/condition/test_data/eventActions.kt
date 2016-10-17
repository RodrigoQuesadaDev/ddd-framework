package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.condition.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.TimesReceivedEvaluator
import com.aticosoft.appointments.mobile.business.domain.model.common.event.TimesReceivedEvaluator.MULTIPLE_TIMES
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ProducedValue
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ValueProducer
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.condition.test_data.LocalProducedValue.ExecutionConditionType
import com.rodrigodev.common.testing.number.isEven
import com.rodrigodev.common.testing.number.isPrime
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
internal abstract class LocalTestEventAction<E : TestEvent>(timesReceived: TimesReceivedEvaluator)
: TestSimpleEventAction<E, LocalTestEventAction<E>, LocalValueProducer<E>, LocalProducedValue>(timesReceived) {

    open val condition: ExecutionCondition = AllCondition()

    init {
        addCondition()
    }

    fun addCondition() {
        condition { condition.isMet(this) }
    }
}

//region Conditions
internal interface ExecutionCondition {
    fun isMet(event: TestEvent): Boolean
}

internal class PrimeCondition : ExecutionCondition {
    override fun isMet(event: TestEvent) = event.value.isPrime()
}

internal class EvenCondition : ExecutionCondition {
    override fun isMet(event: TestEvent) = event.value.isEven()
}

internal class AllCondition : ExecutionCondition {
    override fun isMet(event: TestEvent) = true
}

internal interface BadExecutionCondition : ExecutionCondition

internal class BadModifyingCondition : BadExecutionCondition {
    override fun isMet(event: TestEvent): Boolean {
        ++event.value
        return true
    }
}
//endregion

//region Produced Values
@Singleton
internal class LocalValueProducer<E : TestEvent> @Inject protected constructor() : ValueProducer<E, LocalTestEventAction<E>, LocalProducedValue>() {

    override fun producedValue(eventAction: LocalTestEventAction<E>, value: Int)
            = LocalProducedValue(ExecutionConditionType.from(eventAction), value)
}

internal data class LocalProducedValue(val type: ExecutionConditionType, val value: Int) : ProducedValue {

    override fun toString() = "${type.symbol}:$value"

    enum class ExecutionConditionType(val symbol: String, val conditionClass: Class<out ExecutionCondition>) {
        PRIME("P", PrimeCondition::class.java),
        EVEN("E", EvenCondition::class.java),
        ALL("A", AllCondition::class.java),
        BAD("BAD", BadExecutionCondition::class.java);

        companion object {
            private val symbolsMap = ExecutionConditionType.values().associateBy { it.symbol }

            private val conditionClassesMap = ExecutionConditionType.values().associateBy { it.conditionClass }

            fun from(symbol: String): ExecutionConditionType = symbolsMap[symbol]!!

            fun from(action: LocalTestEventAction<*>): ExecutionConditionType = conditionClassesMap[action.condition.javaClass] ?: BAD
        }
    }
}
//endregion

//region Sample event's actions
@Singleton
internal class SampleEventAction1 @Inject constructor() : LocalTestEventAction<SampleEvent>(MULTIPLE_TIMES) {
    override val priority = 100
    override val condition = PrimeCondition()
}

@Singleton
internal class SampleEventAction2 @Inject constructor() : LocalTestEventAction<SampleEvent>(MULTIPLE_TIMES) {
    override val priority = 90
    override val condition = EvenCondition()
}

@Singleton
internal class SampleEventAction3 @Inject constructor() : LocalTestEventAction<SampleEvent>(MULTIPLE_TIMES) {
    override val priority = 80
    override val condition = AllCondition()
}
//endregion

//region BadCondModifies event's actions
@Singleton
internal class BadCondModifiesEventAction1 @Inject constructor() : LocalTestEventAction<BadCondModifiesEvent>(MULTIPLE_TIMES) {
    override val condition = BadModifyingCondition()
}
//endregion

//region BadCondAddedAfterInit event's actions
@Singleton
internal class BadCondAddedAfterInitEventAction1 @Inject constructor() : LocalTestEventAction<BadCondAddedAfterInitEvent>(MULTIPLE_TIMES)
//endregion