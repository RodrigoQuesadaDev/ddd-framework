@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.executionorder

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStore
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.EmptyValueProducer
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.executionorder.EventActionDoesNotGetExecuted.LocalSteps.LocalEventType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.executionorder.EventActionDoesNotGetExecuted.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.executionorder.test_data.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.EventActionStepBase
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 30/10/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class EventActionDoesNotGetExecuted : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<EventActionDoesNotGetExecuted>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val noActionsEventMembers: LocalEventMembers<NoActionsEvent, NoActionsEventServices>,
            private val threeActionsEventMembers: LocalEventMembers<ThreeActionsEvent, ThreeActionsEventServices>,
            private val fiveActionsEventMembers: LocalEventMembers<FiveActionsEvent, FiveActionsEventServices>
    ) : EventActionStepBase<LocalSteps, LocalEventType, LocalTestEventAction<*>>() {

        override val eventTypeValues = LocalEventType.values()
        override val actionType = LocalTestEventAction::class.java

        @Then("\$eventType actions don't get triggered")
        fun thenLocalEventTypeActionsDontGetTriggered(eventType: LocalEventType) = with(eventType) {
            assertThat(m.valueProducer.producedValues).isEmpty()
        }

        //region Event Members
        @Singleton
        class LocalEventMembers<E : TestEvent, out S : TestEventServices<E>> @Inject protected constructor(
                override val repositoryManager: TestEventRepositoryManager<E>,
                override val eventStoreManager: TestEventStore<E>,
                override val services: S,
                override val valueProducer: EmptyValueProducer<E>
        ) : EventMembers<E, TestEventServices<E>> {

        }
        //endregion

        //region Other Classes
        enum class LocalEventType(
                override val eventClass: Class<out Event>,
                override val eventMembers: LocalSteps.() -> LocalEventMembers<*, *>
        ) : EventType<LocalSteps, LocalEventMembers<*, *>> {
            NO_ACTIONS(NoActionsEvent::class.java, { noActionsEventMembers }),
            THREE_ACTIONS(ThreeActionsEvent::class.java, { threeActionsEventMembers }),
            FIVE_ACTIONS(FiveActionsEvent::class.java, { fiveActionsEventMembers })
        }
        //endregion
    }
}