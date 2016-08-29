package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStoreManager
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.EventSubscriptionCreation.EventType.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.EventSubscriptionCreation.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data.FiveSubscriptionsEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data.NoSubscriptionsEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data.OneSubscriptionEvent
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
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

        @Then("no actions are subscribed to \$eventType event")
        fun thenNoActionsAreSubscribedToEvent(eventType: EventType) {
            assertThat(eventType.eventStoreManager.subscribedActions).isEmpty()
        }

        //region Utils
        private val EventType.eventStoreManager: TestEventStoreManager<*>
            get() = when (this) {
                NO_SUBSCRIPTIONS -> noSubscriptionsEventStore
                ONE_SUBSCRIPTION -> oneSubscriptionEventStore
                FIVE_SUBSCRIPTIONS -> fiveSubscriptionsEventStore
            }
        //endregion
    }

    //region Other Classes
    enum class EventType {NO_SUBSCRIPTIONS, ONE_SUBSCRIPTION, FIVE_SUBSCRIPTIONS }
    //endregion
}