@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.actionbehavior.test_data

import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
@Singleton
internal class NoActionsEventServices @Inject protected constructor(
        private val testEventFactory: NoActionsEventFactory
) : TestEventServices<NoActionsEvent>({ testEventFactory.create(it) })

@Singleton
internal class ThreeActionsEventServices @Inject protected constructor(
        private val testEventFactory: ThreeActionsEventFactory
) : TestEventServices<ThreeActionsEvent>({ testEventFactory.create(it) })

@Singleton
internal class FiveActionsEventServices @Inject protected constructor(
        private val testEventFactory: FiveActionsEventFactory
) : TestEventServices<FiveActionsEvent>({ testEventFactory.create(it) })
