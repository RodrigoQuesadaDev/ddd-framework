@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
@Singleton
internal class TestEventAServices @Inject protected constructor(
        private val testEventFactory: TestEventAFactory,
        private val testEventStore: EventStore<TestEventA>
) : ApplicationServices() {

    class AddEventA(val value: Int) : Command()

    fun execute(command: AddEventA) = command.execute {
        testEventStore.add(testEventFactory.create(value))
    }
}

@Singleton
internal class TestEventBServices @Inject protected constructor(
        private val testEventFactory: TestEventBFactory,
        private val testEventStore: EventStore<TestEventB>
) : ApplicationServices() {

    class AddEventB(val value: Int) : Command()

    fun execute(command: AddEventB) = command.execute {
        testEventStore.add(testEventFactory.create(value))
    }
}