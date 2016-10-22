@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.betweenruns.test_data

import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
@Singleton
internal class SampleEventServices @Inject protected constructor(
        private val testEventFactory: SampleEventFactory
) : TestEventServices<SampleEvent>({ testEventFactory.create(it) })