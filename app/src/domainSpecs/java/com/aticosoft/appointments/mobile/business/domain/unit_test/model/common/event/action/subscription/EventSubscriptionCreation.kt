@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStore
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.EmptyValueProducer
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription.EventSubscriptionCreation.LocalSteps.LocalEventType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription.EventSubscriptionCreation.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription.test_data.FiveSubscriptionsEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription.test_data.LocalTestEventAction
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription.test_data.NoSubscriptionsEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription.test_data.OneSubscriptionEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.EventActionStepBase
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.rodrigodev.common.assertj.Assertions.assertThatList
import com.rodrigodev.common.properties.Delegates.unsupportedOperation
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Alias
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 11/08/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class EventSubscriptionCreation : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<EventSubscriptionCreation>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val noSubscriptionsEventMembers: LocalEventMembers<NoSubscriptionsEvent>,
            private val oneSubscriptionEventMembers: LocalEventMembers<OneSubscriptionEvent>,
            private val fiveSubscriptionsEventMembers: LocalEventMembers<FiveSubscriptionsEvent>
    ) : EventActionStepBase<LocalSteps, LocalEventType, LocalTestEventAction<*>>() {

        override val eventTypeValues = LocalEventType.values()
        override val actionType = LocalTestEventAction::class.java

        @Given("no declared actions for \$eventType event")
        fun givenNoDeclaredActionsForEvent(eventType: LocalEventType) {
            assertThat(eventType.declaredEventActions.toList()).isEmpty()
        }

        @Given("\$eventType event actions with the next ids are declared: [\$ids]")
        fun givenEventActionsWithTheNextIdsAreDeclared(eventType: LocalEventType, ids: MutableList<Int>) {
            assertThatList(eventType.declaredEventActions.toValues().toList())
                    .containsExactlyElementsOfInAnyOrder(ids)
        }

        @Then("no actions are subscribed to \$eventType event")
        fun thenNoActionsAreSubscribedToEvent(eventType: LocalEventType) = with(eventType) {
            assertThat(m.eventStoreManager.simpleActions).isEmpty()
        }

        @Then("only \$totalSubscribedActions action is subscribed to \$eventType event and it has the id \$ids")
        @Alias("only \$totalSubscribedActions actions are subscribed to \$eventType event and they have the ids [\$ids]")
        fun thenOnlyXActionsAreSubscribedToEventAndTheyHaveTheIds(totalSubscribedActions: Int, eventType: LocalEventType, ids: MutableList<Int>) = with(eventType) {
            require(totalSubscribedActions == ids.size)

            assertThatList(
                    m.eventStoreManager.simpleActions.asSequence()
                            .toTestEventActions()
                            .toValues()
                            .toList()
            )
                    .containsExactlyElementsOfInAnyOrder(ids)
        }

        //region Event Members
        @Singleton
        class LocalEventMembers<E : TestEvent> @Inject protected constructor(
                override val repositoryManager: TestEventRepositoryManager<E>,
                override val eventStoreManager: TestEventStore<E>,
                override val valueProducer: EmptyValueProducer<E>
        ) : EventMembers<E, TestEventServices<E>> {
            override val services: TestEventServices<E> by unsupportedOperation()
        }
        //endregion

        //region Other Classes
        enum class LocalEventType(
                override val eventClass: Class<out Event>,
                override val eventMembers: LocalSteps.() -> LocalEventMembers<*>
        ) : EventType<LocalSteps, LocalEventMembers<*>> {
            NO_SUBSCRIPTIONS(NoSubscriptionsEvent::class.java, { noSubscriptionsEventMembers }),
            ONE_SUBSCRIPTION(OneSubscriptionEvent::class.java, { oneSubscriptionEventMembers }),
            FIVE_SUBSCRIPTIONS(FiveSubscriptionsEvent::class.java, { fiveSubscriptionsEventMembers })
        }
        //endregion
    }
}

//region Utils
private inline fun Sequence<EventAction<*>>.toTestEventActions() = map { it as LocalTestEventAction<*> }

private inline fun Sequence<LocalTestEventAction<*>>.toValues() = map { it.value }
//endregion