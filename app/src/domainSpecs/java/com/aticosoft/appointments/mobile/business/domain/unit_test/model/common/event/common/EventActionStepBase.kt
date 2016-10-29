@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStore
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ProducedValue
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestSimpleEventAction.ValueProducer
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.EventActionStepBase.EventType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices.AddEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.declaredActions
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.spec.story.steps.SpecSteps
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.*

/**
 * Created by Rodrigo Quesada on 13/10/16.
 */
internal abstract class EventActionStepBase<S : EventActionStepBase<S, *, *>, T : EventType<S, *>, A : TestSimpleEventAction<*, *, *, *>> : SpecSteps(), ExceptionThrowingSteps {
    private companion object {
        val DEFAULT_EVENT_VALUE = 0
    }

    protected abstract val eventTypeValues: Array<T>
    protected abstract val actionType: Class<A>

    override var _thrownException: Throwable? = null
    override var _catchException: Boolean = false

    @BeforeScenario(uponType = ScenarioType.ANY)
    fun clearValues() {
        eventTypeValues.forEach { with(it) { m.valueProducer.clear() } }
    }

    @Given("\$eventType actions don't modify the event")
    fun givenLocalEventTypeActionsDontModifyTheEvent(eventType: T) = with(eventType) {
        m.eventStoreManager.subscribedTestActions().forEach { it.updateEvent(0) }
    }

    @Given("the \${position}{st|nd|rd|th} \$eventType action that gets executed increments its value{ (at least)|}: \$times time{s|}")
    fun givenTheNthEventActionThatGetsExecutedIncrementsItsValueAtLeastXTimes(position: Int, eventType: T, times: Int): Unit = with(eventType) {
        m.eventStoreManager.subscribedTestActions()
                .filter { it.executionPosition == position - 1 }
                .forEach { it.updateEvent(times) }
    }

    @Given("\$eventType actions execution is suspended")
    fun givenEventActionsExecutionIsSuspended(eventType: T) = with(eventType) {
        m.eventStoreManager.suspendActionsExecution()
    }

    @When("\$eventType actions execution is resumed")
    fun FIVE_ACTIONSFIVE_ACTIONSwhenLocalEventTypeActionsExecutionIsResumed(eventType: T) = with(eventType) {
        m.eventStoreManager.resumeActionsExecution()
    }

    @When("event \$eventType occurs")
    fun whenAnEventOccurs(eventType: T) {
        mightThrowException { whenAnEventOccursWithValue(eventType, DEFAULT_EVENT_VALUE) }
    }

    @When("event \$eventType occurs with value \$value")
    fun whenAnEventOccursWithValue(eventType: T, value: Int) = with(eventType) {
        mightThrowException { m.services.execute(AddEvent(value)) }
    }

    @Then("\$eventType action{s|} produce{d|s|} the next values in order: [\$values]")
    fun thenActionsProduceTheNextValuesInOrder(eventType: T, values: MutableList<ProducedValue>) = with(eventType) {
        assertThat(m.valueProducer.producedValues).containsExactlyElementsOf(values)
    }

    protected fun <E : TestEvent> TestEventStore<E>.subscribedTestActions()
            : List<TestSimpleEventAction<E, *, *, *>> = simpleActions.map { it as TestSimpleEventAction<E, *, *, *> }

    protected val T.declaredEventActions: Sequence<A>
        get() = eventClass.declaredActions(actionType)

    protected val TestEventStore<*>.simpleLocalActions: List<A>
        @Suppress("UNCHECKED_CAST")
        get() = simpleActions.map { it as A }

    interface EventType<S : EventActionStepBase<S, *, *>, out M : EventMembers<*, *>> {
        val eventClass: Class<out Event>
        val eventMembers: S.() -> M
        val EventActionStepBase<*, *, *>.m: M
            @Suppress("UNCHECKED_CAST")
            get() = eventMembers(this as S)
    }

    interface EventMembers<E : TestEvent, out S : TestEventServices<E>> {
        val eventStoreManager: TestEventStore<E>
        val services: S
        val valueProducer: ValueProducer<E, *, *>
    }
}