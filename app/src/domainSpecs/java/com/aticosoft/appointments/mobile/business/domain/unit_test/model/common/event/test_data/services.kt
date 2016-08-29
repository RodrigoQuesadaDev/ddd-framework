@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventStore
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
internal abstract class TestEventServices<E : Event>(
        private val createEvent: (Int) -> E
) : ApplicationServices() {

    private lateinit var m: InjectedMembers<E>

    class AddEvent(val value: Int) : Command()

    fun execute(command: AddEvent) = command.execute {
        m.testEventStore.add(createEvent(value))
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<E>) {
        m = injectedMembers
    }

    class InjectedMembers<E : Event> @Inject protected constructor(
            val testEventStore: EventStore<E>
    )
    //endregion
}

@Singleton
internal class TestEventAServices @Inject protected constructor(
        private val testEventFactory: TestEventAFactory
) : TestEventServices<TestEventA>({ testEventFactory.create(it) })

@Singleton
internal class TestEventBServices @Inject protected constructor(
        private val testEventFactory: TestEventBFactory
) : TestEventServices<TestEventB>({ testEventFactory.create(it) })