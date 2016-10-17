package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.timesreceived.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.model.common.event.TimesReceivedEvaluator
import com.aticosoft.appointments.mobile.business.domain.model.common.event.TimesReceivedEvaluator.MULTIPLE_TIMES
import com.aticosoft.appointments.mobile.business.domain.model.common.event.TimesReceivedEvaluator.SINGLE_TIME
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ProducedValue
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ValueProducer
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.timesreceived.test_data.LocalProducedValue.TimesReceivedType
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
internal abstract class LocalTestEventAction<E : TestEvent>(timesReceived: TimesReceivedEvaluator)
: TestSimpleEventAction<E, LocalValueProducer<E>, LocalProducedValue>(timesReceived)

//region Produced Values
@Singleton
internal class LocalValueProducer<E : TestEvent> @Inject protected constructor() : ValueProducer<E, LocalProducedValue>() {

    override fun producedValue(eventAction: TestEventAction<*>, value: Int)
            = LocalProducedValue(TimesReceivedType.from(eventAction), value)
}

internal data class LocalProducedValue(val type: TimesReceivedType, val value: Int) : ProducedValue {

    override fun toString() = "${type.symbol}:$value"

    enum class TimesReceivedType(val symbol: String) { SINGLE("S"), MULTIPLE("M");

        companion object {
            fun from(symbol: String): TimesReceivedType = when (symbol) {
                "S" -> SINGLE
                "M" -> MULTIPLE
                else -> throw IllegalArgumentException()
            }

            fun from(action: EventAction<*>): TimesReceivedType = when (action.timesReceived) {
                TimesReceivedEvaluator.SINGLE_TIME -> SINGLE
                TimesReceivedEvaluator.MULTIPLE_TIMES -> MULTIPLE
            }
        }
    }
}
//endregion

//region Sample event's actions
@Singleton
internal class SampleEventAction1 @Inject constructor() : LocalTestEventAction<SampleEvent>(MULTIPLE_TIMES) {
    override val priority = 100
}

@Singleton
internal class SampleEventAction2 @Inject constructor() : LocalTestEventAction<SampleEvent>(SINGLE_TIME) {
    override val priority = 90
}

@Singleton
internal class SampleEventAction3 @Inject constructor() : LocalTestEventAction<SampleEvent>(MULTIPLE_TIMES) {
    override val priority = 80
}

@Singleton
internal class SampleEventAction4 @Inject constructor() : LocalTestEventAction<SampleEvent>(SINGLE_TIME) {
    override val priority = 70
}

@Singleton
internal class SampleEventAction5 @Inject constructor() : LocalTestEventAction<SampleEvent>(MULTIPLE_TIMES) {
    override val priority = 60
}
//endregion