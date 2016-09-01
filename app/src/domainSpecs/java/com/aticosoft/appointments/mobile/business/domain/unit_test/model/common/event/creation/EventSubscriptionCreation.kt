@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStoreManager
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.EventSubscriptionCreation.EventType.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.EventSubscriptionCreation.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data.FiveSubscriptionsEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data.NoSubscriptionsEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data.OneSubscriptionEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data.TestEventAction
import com.rodrigodev.common.reflection.anyIsSubOfOrSameAs
import com.rodrigodev.common.reflection.classes
import com.rodrigodev.common.reflection.createReflectionsForPackages
import com.rodrigodev.common.reflection.genericAncestor
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Alias
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

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
            private val noSubscriptionsEventStore: TestEventStoreManager<NoSubscriptionsEvent>,
            private val oneSubscriptionEventStore: TestEventStoreManager<OneSubscriptionEvent>,
            private val fiveSubscriptionsEventStore: TestEventStoreManager<FiveSubscriptionsEvent>
    ) {
        @Given("no declared actions for \$eventType event")
        fun givenNoDeclaredActionsForEvent(eventType: EventType) {
        }

        @Given("\$eventType event actions with the next ids are declared: [\$ids]")
        fun givenEventActionsWithTheNextIdsAreDeclared(eventType: EventType, ids: MutableList<Int>) {
            assertThat(eventType.declaredEventActions
                    .toValues()
                    .toList()
            )
                    .containsOnlyElementsOf(ids)
                    .doesNotHaveDuplicates()
        }

        @Then("no actions are subscribed to \$eventType event")
        fun thenNoActionsAreSubscribedToEvent(eventType: EventType) {
            assertThat(eventType.eventStoreManager.subscribedActions).isEmpty()
        }

        @Then("only \$totalSubscribedActions action is subscribed to \$eventType event and it has the id \$ids")
        @Alias("only \$totalSubscribedActions actions are subscribed to \$eventType event and they have the ids [\$ids]")
        fun thenOnlyXActionsAreSubscribedToEventAndTheyHaveTheIds(totalSubscribedActions: Int, eventType: EventType, ids: MutableList<Int>) {
            require(totalSubscribedActions == ids.size)

            val subscribedActions = eventType.eventStoreManager.subscribedActions
            assertThat(subscribedActions).hasSize(totalSubscribedActions)
            assertThat(
                    subscribedActions.asSequence()
                            .toTestEventActions()
                            .toValues()
                            .toList()
            )
                    .containsOnlyElementsOf(ids)
        }

        //region Utils
        private val EventType.eventStoreManager: TestEventStoreManager<*>
            get() = when (this) {
                NO_SUBSCRIPTIONS -> noSubscriptionsEventStore
                ONE_SUBSCRIPTION -> oneSubscriptionEventStore
                FIVE_SUBSCRIPTIONS -> fiveSubscriptionsEventStore
            }

        private val EventType.eventClass: Class<out Event>
            get() = when (this) {
                NO_SUBSCRIPTIONS -> NoSubscriptionsEvent::class.java
                ONE_SUBSCRIPTION -> OneSubscriptionEvent::class.java
                FIVE_SUBSCRIPTIONS -> FiveSubscriptionsEvent::class.java
            }

        private val EventType.declaredEventActions: Sequence<TestEventAction<*>>
            get() {
                data class TypeInfo(val type: Class<*>) {
                    val testEventActionAncestor: ParameterizedType = type.genericAncestor(TestEventAction::class.java) as ParameterizedType
                }

                return createReflectionsForPackages(eventClass.`package`)
                        .getSubTypesOf(TestEventAction::class.java).asSequence()
                        .map { TypeInfo(it) }
                        .filter { it.testEventActionAncestor.actualTypeArguments[0].classes().anyIsSubOfOrSameAs(eventClass) }
                        .map { it.type.newInstance() as TestEventAction<*> }
            }
        //endregion
    }

    //region Other Classes
    enum class EventType {NO_SUBSCRIPTIONS, ONE_SUBSCRIPTION, FIVE_SUBSCRIPTIONS }
    //endregion
}

//region Utils
private inline fun Sequence<EventAction<*>>.toTestEventActions() = map { it as TestEventAction<*> }

private fun Sequence<TestEventAction<*>>.toValues() = map { it.value }
//endregion