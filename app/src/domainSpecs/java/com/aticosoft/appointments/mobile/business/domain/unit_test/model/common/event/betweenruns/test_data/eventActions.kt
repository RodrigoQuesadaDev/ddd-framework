package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.betweenruns.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.TimesReceivedEvaluator
import com.aticosoft.appointments.mobile.business.domain.model.common.event.TimesReceivedEvaluator.MULTIPLE_TIMES
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ProducedValue
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ValueProducer
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
internal abstract class LocalTestEventAction<E : TestEvent>(val id: Int, timesReceived: TimesReceivedEvaluator)
: TestSimpleEventAction<E, LocalTestEventAction<E>, LocalValueProducer<E>, LocalProducedValue>(timesReceived)

//region Produced Values
@Singleton
internal class LocalValueProducer<E : TestEvent> @Inject protected constructor() : ValueProducer<E, LocalTestEventAction<E>, LocalProducedValue>() {

    override fun producedValue(eventAction: LocalTestEventAction<E>, value: Int)
            = LocalProducedValue(1, value)
}

internal data class LocalProducedValue(val id: Int, val value: Int) : ProducedValue {

    override fun toString() = "id$id:$value"
}
//endregion

//region Sample event's actions
@Singleton
internal class SampleEventAction1 @Inject constructor() : LocalTestEventAction<SampleEvent>(1, MULTIPLE_TIMES)

@Singleton
internal class SampleEventAction2 @Inject constructor() : LocalTestEventAction<SampleEvent>(2, MULTIPLE_TIMES)

@Singleton
internal class SampleEventAction3 @Inject constructor() : LocalTestEventAction<SampleEvent>(3, MULTIPLE_TIMES)

@Singleton
internal class SampleEventAction4 @Inject constructor() : LocalTestEventAction<SampleEvent>(4, MULTIPLE_TIMES)

@Singleton
internal class SampleEventAction5 @Inject constructor() : LocalTestEventAction<SampleEvent>(5, MULTIPLE_TIMES)

@Singleton
internal class SampleEventAction6 @Inject constructor() : LocalTestEventAction<SampleEvent>(6, MULTIPLE_TIMES)

@Singleton
internal class SampleEventAction7 @Inject constructor() : LocalTestEventAction<SampleEvent>(7, MULTIPLE_TIMES)

@Singleton
internal class SampleEventAction8 @Inject constructor() : LocalTestEventAction<SampleEvent>(8, MULTIPLE_TIMES)

@Singleton
internal class SampleEventAction9 @Inject constructor() : LocalTestEventAction<SampleEvent>(9, MULTIPLE_TIMES)

@Singleton
internal class SampleEventAction10 @Inject constructor() : LocalTestEventAction<SampleEvent>(10, MULTIPLE_TIMES)
//endregion