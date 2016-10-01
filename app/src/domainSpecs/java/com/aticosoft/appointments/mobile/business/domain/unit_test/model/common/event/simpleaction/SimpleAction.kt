@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.isEmpty
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStoreManager
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices.AddEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.SimpleAction.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data.*
import com.rodrigodev.common.spec.story.converter.ParameterConverterBase
import com.rodrigodev.common.spec.story.steps.SpecSteps
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.jbehave.core.steps.ParameterConverters
import org.jbehave.core.steps.ParameterConverters.ParameterConvertionFailed
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 11/08/16.
 */
@org.robolectric.annotation.Config(application = UnitTestApplicationImpl::class)
internal class SimpleAction : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<SimpleAction>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: SimpleAction.LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val eventNoSubsMembers: EventMembers<TestEventNoSubs, TestEventNoSubsServices>,
            private val eventThreeSubsMembers: EventMembers<TestEventThreeSubs, TestEventThreeSubsServices>,
            private val eventFiveSubsMembers: EventMembers<TestEventFiveSubs, TestEventFiveSubsServices>
    ) : SpecSteps() {
        private companion object {
            val DEFAULT_EVENT_VALUE = 0
        }

        override val converters: Array<ParameterConverters.ParameterConverter> = arrayOf(ProducedValueConverter())

        @Given("\$eventType actions don't modify the event")
        fun givenEventTypeActionsDontModifyTheEvent(eventType: SimpleAction.EventType) {
            eventType.m.eventStoreManager.subscribedTestActions().forEach { it.updateEvent(false) }
        }

        @Given("the \${executionPosition}{st|nd|rd|th} \$eventType action that gets executed increments its value \$times time{s|}")
        fun givenTheNthEventActionThatGetsExecutedIncrementsItsValueXTimes(executionPosition: Int, eventType: EventType, times: Int) {

        }

        @Given("\$eventType actions execution is suspended")
        fun givenEventActionsExecutionIsSuspended(eventType: EventType) {
            eventType.m.eventStoreManager.suspendActionsExecution()
        }

        @When("event \$eventType occurs")
        fun whenAnEventOccurs(eventType: EventType) {
            whenAnEventOccursWithValue(eventType, DEFAULT_EVENT_VALUE)
        }

        @When("event \$eventType occurs with value \$value")
        fun whenAnEventOccursWithValue(eventType: EventType, value: Int) {
            eventType.m.services.execute(AddEvent(value))
        }

        @When("\$eventType actions execution is resumed")
        fun whenThreeSubsActionsExecutionIsResumed(eventType: EventType) {
            eventType.m.eventStoreManager.resumeActionsExecution()
        }

        @Then("\$eventType actions don't get triggered")
        fun thenEventTypeActionsDontGetTriggered(eventType: EventType) {
            assertThat(eventType.m.eventStoreManager.subscribedTestActions()).are(Triggered(false))
        }

        @Then("\$eventType actions produce the next values in order: [\$values]")
        fun thenActionsProduceTheNextValuesInOrder(eventType: EventType, values: MutableList<TestEventAction.ProducedValue>) {
            assertThat(eventType.m.valueProducer.producedValues).containsExactlyElementsOf(values)
        }

        @Then("there are no \$eventType events left")
        fun thenThereAreNoEventsLeft(eventType: EventType) {
            assertThat(eventType.m.repository.isEmpty()).isTrue()
        }

        //region Event Members
        @Singleton
        class EventMembers<E : Event, out S : TestEventServices<E>> @Inject protected constructor(
                val repository: EventRepository<E>,
                val services: S,
                val eventStoreManager: TestEventStoreManager<E>,
                val valueProducer: TestEventAction.ValueProducer<E>
        )
        //endregion

        //region Utils
        private val EventType.m: EventMembers<*, *>
            get() = when (this) {
            //TODO trying to import EventType enums causes: Recursion detected on input: IMPORT_DIRECTIVE... error
                EventType.NO_SUBS -> eventNoSubsMembers
                EventType.THREE_SUBS -> eventThreeSubsMembers
                EventType.FIVE_SUBS -> eventFiveSubsMembers
            }

        private inline fun <E : Event> TestEventStoreManager<E>.subscribedTestActions() = subscribedActions.map { it as TestEventAction<E> }
        //endregion
    }

    //region Other Classes
    enum class EventType {NO_SUBS, THREE_SUBS, FIVE_SUBS }

    class ProducedValueConverter : ParameterConverterBase<TestEventAction.ProducedValue>(TestEventAction.ProducedValue::class.java) {
        private companion object {
            //a1:3
            val VALUE_PATTERN = Regex("a(\\d+):(\\d+)", RegexOption.IGNORE_CASE)
        }

        override fun convertValue(value: String, type: Type): TestEventAction.ProducedValue {
            try {
                with(VALUE_PATTERN.matchEntire(value)!!) {
                    return TestEventAction.ProducedValue(
                            groups[1]!!.value.toInt(),
                            groups[2]!!.value.toInt()
                    )
                }
            }
            catch(e: Exception) {
                throw ParameterConvertionFailed("Unable to convert value to ProducedValue.", e)
            }
        }
    }
    //endregion
}

//region Assertions
private class Triggered(private val wasTriggered: Boolean) : Condition<TestEventAction<*>>() {

    override fun matches(action: TestEventAction<*>) = action.wasTriggered == wasTriggered
}
//endregion