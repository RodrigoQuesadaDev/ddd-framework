package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.garbage.actionbehavior.test_data

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
internal abstract class LocalTestEventAction<E : TestEvent> : TestSimpleEventAction<E, LocalTestEventAction<E>, LocalValueProducer<E>, LocalProducedValue>(MULTIPLE_TIMES)

//region Produced Values
@Singleton
internal class LocalValueProducer<E : TestEvent> @Inject protected constructor() : ValueProducer<E, LocalTestEventAction<E>, LocalProducedValue>() {

    override fun producedValue(eventAction: LocalTestEventAction<E>, value: Int)
            = LocalProducedValue(eventAction.executionPosition + 1, value)
}

internal data class LocalProducedValue(val actionExecutionPosition: Int, val value: Int) : ProducedValue {

    override fun toString() = "a$actionExecutionPosition:$value"
}
//endregion

//region ThreeSubscriptions event's actions
@Singleton
internal class ThreeSubscriptionsEventAction1 @Inject constructor() : LocalTestEventAction<ThreeActionsEvent>()

@Singleton
internal class ThreeSubscriptionsEventAction2 @Inject constructor() : LocalTestEventAction<ThreeActionsEvent>()

@Singleton
internal class ThreeSubscriptionsEventAction3 @Inject constructor() : LocalTestEventAction<ThreeActionsEvent>()
//endregion

//region FiveSubscriptions event's actions
@Singleton
internal class FiveSubscriptionsEventAction1 @Inject constructor() : LocalTestEventAction<FiveActionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction2 @Inject constructor() : LocalTestEventAction<FiveActionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction3 @Inject constructor() : LocalTestEventAction<FiveActionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction4 @Inject constructor() : LocalTestEventAction<FiveActionsEvent>()

@Singleton
internal class FiveSubscriptionsEventAction5 @Inject constructor() : LocalTestEventAction<FiveActionsEvent>()
//endregion