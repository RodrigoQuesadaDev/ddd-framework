@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.garbage.priority.test_data

import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
@Singleton
internal class SamePriorityEventServices @Inject protected constructor(
        private val testEventFactory: SamePriorityEventFactory
) : TestEventServices<SamePriorityEvent>({ testEventFactory.create(it) })

@Singleton
internal class DifferentPriorityEventServices @Inject protected constructor(
        private val testEventFactory: DifferentPriorityEventFactory
) : TestEventServices<DifferentPriorityEvent>({ testEventFactory.create(it) })

@Singleton
internal class DefaultPriorityEventServices @Inject protected constructor(
        private val testEventFactory: DefaultPriorityEventFactory
) : TestEventServices<DefaultPriorityEvent>({ testEventFactory.create(it) })
