package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.SimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEventAction
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
internal abstract class LocalTestEventAction<E : Event>(val value: Int) : TestEventAction<E>(), SimpleEventAction<E> {

    override fun execute(event: E) {
    }
}

//region OneSubscriptionEvent
@Singleton
internal class OneSubscriptionEventAction1 @Inject constructor() : LocalTestEventAction<OneSubscriptionEvent>(1)
//endregion

//region OneSubscriptionEvent
@Singleton
internal class FiveSubscriptionsEventAction1 @Inject constructor() : LocalTestEventAction<FiveSubscriptionsEvent>(1)

@Singleton
internal class FiveSubscriptionsEventAction2 @Inject constructor() : LocalTestEventAction<FiveSubscriptionsEvent>(2)

@Singleton
internal class FiveSubscriptionsEventAction3 @Inject constructor() : LocalTestEventAction<FiveSubscriptionsEvent>(3)

@Singleton
internal class FiveSubscriptionsEventAction4 @Inject constructor() : LocalTestEventAction<FiveSubscriptionsEvent>(4)

@Singleton
internal class FiveSubscriptionsEventAction5 @Inject constructor() : LocalTestEventAction<FiveSubscriptionsEvent>(5)
//endregion