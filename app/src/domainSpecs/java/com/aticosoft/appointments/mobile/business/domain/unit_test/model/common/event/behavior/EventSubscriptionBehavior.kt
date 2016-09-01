@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.behavior

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStoreManager
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.behavior.EventSubscriptionBehavior.EventType.A
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.behavior.EventSubscriptionBehavior.EventType.B
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.behavior.EventSubscriptionBehavior.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.behavior.test_data.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices.AddEvent
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.test.catchThrowable
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Pending
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 11/08/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class EventSubscriptionBehavior : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<EventSubscriptionBehavior>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val eventAMembers: EventMembers<TestEventA, TestEventAServices>,
            private val eventBMembers: EventMembers<TestEventB, TestEventBServices>
    ) : ExceptionThrowingSteps {

        override var throwable: Throwable? = null

        private companion object {
            val DEFAULT_EVENT_VALUE = 0
        }

        private object DESC {
            const val EVENT_GETS_SUBSCRIBED = "event \$eventType gets subscribed \$subsNumber times"
            const val EVENT_OCCURS_WITH_VALUE = "event \$eventType occurs with value \$value"
        }

        @Given("the actions subscribed to event \$eventType don't update it")
        fun givenTheActionsSubscribedToEventDontUpdateIt(eventType: EventType) {
            eventType.m.eventStoreManager.subscribedTestActions().forEach { it.updateEvent(false) }
        }

        @Given(DESC.EVENT_GETS_SUBSCRIBED)
        @When(DESC.EVENT_GETS_SUBSCRIBED)
        fun givenEventGetsSubscribed(eventType: EventType, subsNumber: Int) {
            eventType.m.eventGetsSubscribed(subsNumber)
        }

        private fun <E : Event> EventMembers<E, *>.eventGetsSubscribed(subsNumber: Int) {
            repeat(subsNumber) { eventStoreManager.subscribeAction(TestEventAction(it)) }
        }

        @When("event \$eventType occurs")
        fun whenAnEventOccurs(eventType: EventType) {
            throwable = catchThrowable { eventType.m.services.execute(AddEvent(DEFAULT_EVENT_VALUE)) }
        }

        @Then("no exception is thrown")
        fun thenNoExceptionIsThrown() {
            assertThat(throwable).isNull()
        }

        @Then("actions subscribed to event \$eventType don't get triggered")
        fun thenActionsSubscribedToEventDontGetTriggered(eventType: EventType) {
            assertThat(eventType.m.eventStoreManager.subscribedTestActions()).are(Triggered(false))
        }

        @Then("only 1 of the actions subscribed to event \$eventType gets triggered, and only once")
        fun thenOnly1OfTheActionsSubscribedToEventGetsTriggeredAndOnlyOnce(eventType: EventType) {
            assertThat(eventType.m.eventStoreManager.subscribedTestActions()).areExactly(1, Triggered(true))
        }

        @Then("that event A got removed")
        @Pending
        fun thenThatEventGotRemoved() {
        }

        @Given("the actions subscribed to event \$eventType increment its value each time they are executed except when the value is equal or greater than \$untilValue")
        @Pending
        fun givenTheActionsSubscribedToEventIncrementItsValueUntil(eventType: EventType, untilValue: Int) {
        }

        @Given(DESC.EVENT_OCCURS_WITH_VALUE)
        @When(DESC.EVENT_OCCURS_WITH_VALUE)
        @Pending
        fun givenEventOccursWithValue(eventType: EventType, value: Int) {
        }

        @Then("if only 1 action is subscribed to event \$eventType, it gets triggered \$times times")
        @Pending
        fun thenIfOnly1ActionIsSubscribedToEventItGetsTriggeredXTimes(eventType: EventType, times: Int) {
        }

        @Then("if more than 1 action is subscribed to event \$eventType, they get triggered \$triggerTimes times in total and the individual amount of times they get triggered doesn't differ from the others by more than \$triggerAmountDifference")
        @Pending
        fun thenIfMoreThan1ActionIsSubscribedToEventTheyGetTriggeredXTimes(eventType: EventType, triggerTimes: Int, triggerAmountDifference: Int) {
        }

        @Then("actions subscribed to event \$eventType get triggered with the values [\$values], in that order")
        @Pending
        fun thenActionsSubscribedToEventGetTriggeredWithTheValuesInThatOrder(eventType: EventType, values: MutableList<Int>) {
        }

        @Given("the actions subscribed to event \$eventType marks it to be kept during \$times executions")
        @Pending
        fun givenTheActionsSubscribedToEventMarksItToBeKeptDuringXExecutions(eventType: EventType, times: Int) {
        }

        @Then("actions subscribed to event \$eventType get triggered \$times1*\$times2 times with the value \$value")
        @Pending
        fun thenActionsSubscribedToEventGetTriggeredXTimesWithTheValue(eventType: EventType, times1: Int, times2: Int, value: Int) {
        }

        @Given("no actions subscribed")
        @Pending
        fun givenNoActionsSubscribed() {
        }

        @Given("the actions that subscribe to event \$eventType don't update it")
        @Pending
        fun givenTheActionsThatSubscribeToEventDontUpdateIt(eventType: EventType) {
        }

        @Given("event \$eventType occurs \$times times with the values [\$values]")
        @Pending
        fun givenEventAOccursXTimesWithTheValues(eventType: EventType, times: Int, values: MutableList<Int>) {
        }

        @Then("if more than 1 action is subscribed to event \$eventType, they get triggered \$times times in total")
        @Pending
        fun thenIfMoreThanActionIsSubscribedToEventTheyGetTriggeredXTimesInTotal(eventType: EventType, times: Int) {
        }

        //region Event Members
        @Singleton
        class EventMembers<E : Event, out S : TestEventServices<E>> @Inject protected constructor(
                val services: S,
                val eventStoreManager: TestEventStoreManager<E>
        )
        //endregion

        //region Utils
        private val EventType.m: EventMembers<*, *>
            get() = when (this) {
                A -> eventAMembers
                B -> eventBMembers
            }

        private inline fun <E : Event> TestEventStoreManager<E>.subscribedTestActions() = subscribedActions.map { it as TestEventAction<E> }
        //endregion
    }

    //region Other Classes
    enum class EventType {A, B }
    //endregion
}

//region Assertions
private class Triggered(private val wasTriggered: Boolean) : Condition<TestEventAction<*>>() {

    override fun matches(action: TestEventAction<*>) = action.wasTriggered == wasTriggered
}
//endregion