package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.EventSubscription.EventType.A
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.EventSubscription.EventType.B
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.EventSubscription.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.test_data.TestEventAServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.test_data.TestEventBServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.test_data.TestEventServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.test_data.TestEventServices.AddEvent
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.test.catchThrowable
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Pending
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 11/08/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class EventSubscription : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<EventSubscription>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val eventAServices: TestEventAServices,
            private val eventBServices: TestEventBServices
    ) : ExceptionThrowingSteps {

        override var throwable: Throwable? = null

        private companion object {
            val DEFAULT_EVENT_VALUE = 0
        }

        @When("event \$eventType occurs")
        fun whenAnEventOccurs(eventType: EventType) {
            throwable = catchThrowable { eventType.eventServices.execute(AddEvent(DEFAULT_EVENT_VALUE)) }
        }

        @Then("no exception is thrown")
        fun thenNoExceptionIsThrown() {
            assertThat(throwable).isNull()
        }

        @Given("event \$eventType gets subscribed \$subsNumber times")
        @Pending
        fun givenEventGetsSubscribed(eventType: EventType, times: Int) {
        }

        @When("event \$eventType gets subscribed \$subsNumber times")
        @Pending
        fun whenEventGetsSubscribed(eventType: EventType, times: Int) {
            givenEventGetsSubscribed(eventType, times)
        }

        @Then("actions subscribed to event \$eventType don't get triggered")
        @Pending
        fun thenActionsSubscribedToEventDontGetTriggered() {
        }

        @Given("the actions subscribed to event \$eventType don't update it")
        @Pending
        fun givenTheActionsSubscribedToEventDontUpdateIt(eventType: EventType) {
        }

        @Then("only 1 of the actions subscribed to event \$eventType gets triggered, and only once")
        @Pending
        fun thenOnly1OfTheActionsSubscribedToEventGetsTriggeredAndOnlyOnce(eventType: EventType) {
        }

        @Then("that event A got removed")
        @Pending
        fun thenThatEventGotRemoved() {
        }

        @Given("the actions subscribed to event \$eventType increment its value each time they are executed except when the value is equal or greater than \$untilValue")
        @Pending
        fun givenTheActionsSubscribedToEventIncrementItsValueUntil(eventType: EventType, untilValue: Int) {
        }

        @Given("event \$eventType occurs with value \$value")
        @Pending
        fun givenEventOccursWithValue(eventType: EventType, value: Int) {
        }

        @When("event \$eventType occurs with value \$value")
        @Pending
        fun whenEventOccursWithValue(eventType: EventType, value: Int) {
            givenEventOccursWithValue(eventType, value)
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

        //region Utils
        private val EventType.eventServices: TestEventServices<*>
            get() = when (this) {
                A -> eventAServices
                B -> eventBServices
            }
        //endregion
    }

    //region Other Classes
    enum class EventType {A, B }
    //endregion
}