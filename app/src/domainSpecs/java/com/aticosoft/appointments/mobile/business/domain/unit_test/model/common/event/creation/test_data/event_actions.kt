package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.SimpleEventAction
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.EventActionBase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
internal abstract class TestEventAction<E : Event>(val value: Int) : EventActionBase<E>(), SimpleEventAction<E> {

    override fun execute(event: E) {
    }
}

//region OneSubscriptionEvent
@Singleton
internal class OneSubscriptionEventAction1 @Inject constructor() : TestEventAction<OneSubscriptionEvent>(1)
//endregion

//region OneSubscriptionEvent
@Singleton
internal class FiveSubscriptionsEventAction1 @Inject constructor() : TestEventAction<FiveSubscriptionsEvent>(1)

@Singleton
internal class FiveSubscriptionsEventAction2 @Inject constructor() : TestEventAction<FiveSubscriptionsEvent>(2)

@Singleton
internal class FiveSubscriptionsEventAction3 @Inject constructor() : TestEventAction<FiveSubscriptionsEvent>(3)

@Singleton
internal class FiveSubscriptionsEventAction4 @Inject constructor() : TestEventAction<FiveSubscriptionsEvent>(4)

@Singleton
internal class FiveSubscriptionsEventAction5 @Inject constructor() : TestEventAction<FiveSubscriptionsEvent>(5)
//endregion