package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription.test_data

import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 25/08/16.
 */
@PersistenceCapable
//@AutoFactory
internal class NoSubscriptionsEvent(value: Int) : TestEvent(value)

@PersistenceCapable
//@AutoFactory
internal class OneSubscriptionEvent(value: Int) : TestEvent(value)

@PersistenceCapable
//@AutoFactory
internal class FiveSubscriptionsEvent(value: Int) : TestEvent(value)