package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.test_data

import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.google.auto.factory.AutoFactory
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 25/08/16.
 */
@PersistenceCapable
@AutoFactory
internal class NoSubscriptionsEntity(c: Context, value: Int) : AbstractTestData(c, value)

@PersistenceCapable
@AutoFactory
internal class OneSubscriptionEntity(c: Context, value: Int) : AbstractTestData(c, value)

@PersistenceCapable
@AutoFactory
internal class FiveSubscriptionsEntity(c: Context, value: Int) : AbstractTestData(c, value)