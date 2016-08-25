package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.EventSubscription.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.test_data.event.TestEventServices
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
            private val eventServices: TestEventServices
    ) {
        @When("event \$eventName occurs")
        @Pending
        fun whenAnEventOccurs(eventName: String) {

        }

        @Then("no exception is thrown")
        @Pending
        fun thenNoExceptionIsThrown() {
        }

        @Given("event \$eventName gets subscribed \$subsNumber times")
        @Pending
        fun givenEventGetsSubscribed(eventName: String, times: Int) {
        }

        @When("event \$eventName gets subscribed \$subsNumber times")
        @Pending
        fun whenEventGetsSubscribed(eventName: String, times: Int) {
            givenEventGetsSubscribed(eventName, times)
        }

        @Then("actions subscribed to event \$eventName don't get triggered")
        @Pending
        fun thenActionsSubscribedToEventDontGetTriggered() {
        }

        @Given("the actions subscribed to event \$eventName don't update it")
        @Pending
        fun givenTheActionsSubscribedToEventDontUpdateIt(eventName: String) {
        }

        @Then("only 1 of the actions subscribed to event \$eventName gets triggered, and only once")
        @Pending
        fun thenOnly1OfTheActionsSubscribedToEventGetsTriggeredAndOnlyOnce(eventName: String) {
        }

        @Then("that event A got removed")
        @Pending
        fun thenThatEventGotRemoved() {
        }

        @Given("the actions subscribed to event \$eventName increment its value each time they are executed except when the value is equal or greater than \$untilValue")
        @Pending
        fun givenTheActionsSubscribedToEventIncrementItsValueUntil(eventName: String, untilValue: Int) {
        }

        @Given("event \$eventName occurs with value \$value")
        @Pending
        fun givenEventOccursWithValue(eventName: String, value: Int) {
        }

        @When("event \$eventName occurs with value \$value")
        @Pending
        fun whenEventOccursWithValue(eventName: String, value: Int) {
            givenEventOccursWithValue(eventName, value)
        }

        @Then("if only 1 action is subscribed to event \$eventName, it gets triggered \$times times")
        @Pending
        fun thenIfOnly1ActionIsSubscribedToEventItGetsTriggeredXTimes(eventName: String, times: Int) {
        }

        @Then("if more than 1 action is subscribed to event \$eventName, they get triggered \$triggerTimes times in total and the individual amount of times they get triggered doesn't differ from the others by more than \$triggerAmountDifference")
        @Pending
        fun thenIfMoreThan1ActionIsSubscribedToEventTheyGetTriggeredXTimes(eventName: String, triggerTimes: Int, triggerAmountDifference: Int) {
        }

        @Then("actions subscribed to event \$eventName get triggered with the values [\$values], in that order")
        @Pending
        fun thenActionsSubscribedToEventGetTriggeredWithTheValuesInThatOrder(eventName: String, values: MutableList<Int>) {
        }

        @Given("the actions subscribed to event \$eventName marks it to be kept during \$times executions")
        @Pending
        fun givenTheActionsSubscribedToEventMarksItToBeKeptDuringXExecutions(eventName: String, times: Int) {
        }

        @Then("actions subscribed to event \$eventName get triggered \$times1*\$times2 times with the value \$value")
        @Pending
        fun thenActionsSubscribedToEventGetTriggeredXTimesWithTheValue(eventName: String, times1: Int, times2: Int, value: Int) {
        }

        @Given("no actions subscribed")
        @Pending
        fun givenNoActionsSubscribed() {
        }

        @Given("the actions that subscribe to event \$eventName don't update it")
        @Pending
        fun givenTheActionsThatSubscribeToEventDontUpdateIt(eventName: String) {
        }

        @Given("event \$eventName occurs \$times times with the values [\$values]")
        @Pending
        fun givenEventAOccursXTimesWithTheValues(eventName: String, times: Int, values: MutableList<Int>) {
        }

        @Then("if more than 1 action is subscribed to event \$eventName, they get triggered \$times times in total")
        @Pending
        fun thenIfMoreThanActionIsSubscribedToEventTheyGetTriggeredXTimesInTotal(eventName: String, times: Int) {
        }
    }
}