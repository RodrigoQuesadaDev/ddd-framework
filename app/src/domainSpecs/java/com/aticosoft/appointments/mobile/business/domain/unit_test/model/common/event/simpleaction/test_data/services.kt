@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data

import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
@Singleton
internal class NoSubscriptionsEventServices @Inject protected constructor(
        private val testEventFactory: NoSubscriptionsEventFactory
) : TestEventServices<NoSubscriptionsEvent>({ testEventFactory.create(it) })

@Singleton
internal class ThreeSubscriptionsEventServices @Inject protected constructor(
        private val testEventFactory: ThreeSubscriptionsEventFactory
) : TestEventServices<ThreeSubscriptionsEvent>({ testEventFactory.create(it) })

@Singleton
internal class FiveSubscriptionsEventServices @Inject protected constructor(
        private val testEventFactory: FiveSubscriptionsEventFactory
) : TestEventServices<FiveSubscriptionsEvent>({ testEventFactory.create(it) })
