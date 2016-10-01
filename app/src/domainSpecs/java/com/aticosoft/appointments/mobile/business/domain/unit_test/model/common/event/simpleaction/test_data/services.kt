@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data

import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
@Singleton
internal class TestEventNoSubsServices @Inject protected constructor(
        private val testEventFactory: TestEventNoSubsFactory
) : TestEventServices<TestEventNoSubs>({ testEventFactory.create(it) })

@Singleton
internal class TestEventThreeSubsServices @Inject protected constructor(
        private val testEventFactory: TestEventThreeSubsFactory
) : TestEventServices<TestEventThreeSubs>({ testEventFactory.create(it) })

@Singleton
internal class TestEventFiveSubsServices @Inject protected constructor(
        private val testEventFactory: TestEventFiveSubsFactory
) : TestEventServices<TestEventFiveSubs>({ testEventFactory.create(it) })
