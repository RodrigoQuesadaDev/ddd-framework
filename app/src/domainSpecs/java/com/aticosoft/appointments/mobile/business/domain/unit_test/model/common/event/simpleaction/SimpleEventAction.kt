@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.isEmpty
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStore
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices.AddEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.declaredActions
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.SimpleEventAction.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data.*
import com.rodrigodev.common.spec.story.converter.ParameterConverterBase
import com.rodrigodev.common.spec.story.steps.SpecSteps
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.*
import org.jbehave.core.steps.ParameterConverters
import org.jbehave.core.steps.ParameterConverters.ParameterConvertionFailed
import org.robolectric.annotation.Config
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 11/08/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class SimpleEventAction : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<SimpleEventAction>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: SimpleEventAction.LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val noSubscriptionsEventMembers: EventMembers<NoSubscriptionsEvent, NoSubscriptionsEventServices>,
            private val threeSubscriptionsEventMembers: EventMembers<ThreeSubscriptionsEvent, ThreeSubscriptionsEventServices>,
            private val fiveSubscriptionsEventMembers: EventMembers<FiveSubscriptionsEvent, FiveSubscriptionsEventServices>
    ) : SpecSteps() {
        private companion object {
            val DEFAULT_EVENT_VALUE = 0
        }

        override val converters: Array<ParameterConverters.ParameterConverter> = arrayOf(ProducedValueConverter())

        @BeforeScenario(uponType = ScenarioType.ANY)
        fun clearValues() {
            EventType.values().forEach { it.m.valueProducer.clear() }
        }

        @Given("event \$eventType has \$n actions subscribed")
        fun givenEventHasNActionsSubscribed(eventType: EventType, n: Int) {
            assertThat(eventType.declaredEventActions.toList()).hasSize(n)
        }

        @Given("\$eventType actions don't modify the event")
        fun givenEventTypeActionsDontModifyTheEvent(eventType: EventType) {
            eventType.m.eventStoreManager.subscribedTestActions().forEach { it.updateEvent(0) }
        }

        @Given("the \${position}{st|nd|rd|th} \$eventType action that gets executed increments its value \$times time{s|}")
        fun givenTheNthEventActionThatGetsExecutedIncrementsItsValueXTimes(position: Int, eventType: EventType, times: Int) {
            eventType.m.eventStoreManager.subscribedTestActions()
                    .filter { it.executionPosition == position - 1 }
                    .forEach { it.updateEvent(times) }
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
        fun whenEventTypeActionsExecutionIsResumed(eventType: EventType) {
            eventType.m.eventStoreManager.resumeActionsExecution()
        }

        @Then("\$eventType actions don't get triggered")
        fun thenEventTypeActionsDontGetTriggered(eventType: EventType) {
            assertThat(eventType.m.valueProducer.producedValues).isEmpty()
        }

        @Then("\$eventType actions produce the next values in order: [\$values]")
        fun thenActionsProduceTheNextValuesInOrder(eventType: EventType, values: MutableList<LocalTestEventAction.ProducedValue>) {
            assertThat(eventType.m.valueProducer.producedValues).containsExactlyElementsOf(values)
        }

        @Then("there are no \$eventType events left")
        fun thenThereAreNoEventsLeft(eventType: EventType) {
            assertThat(eventType.m.repositoryManager.repository { isEmpty() }).isTrue()
        }

        //region Event Members
        @Singleton
        class EventMembers<E : TestEvent, out S : TestEventServices<E>> @Inject protected constructor(
                val repositoryManager: TestEventRepositoryManager<E>,
                val services: S,
                val eventStoreManager: TestEventStore<E>,
                val valueProducer: LocalTestEventAction.ValueProducer<E>
        )
        //endregion

        //region Utils
        private val EventType.m: EventMembers<*, *>
            get() = when (this) {
            //TODO trying to import EventType enums causes: Recursion detected on input: IMPORT_DIRECTIVE... error
                EventType.NO_SUBSCRIPTIONS -> noSubscriptionsEventMembers
                EventType.THREE_SUBSCRIPTIONS -> threeSubscriptionsEventMembers
                EventType.FIVE_SUBSCRIPTIONS -> fiveSubscriptionsEventMembers
            }

        private val EventType.eventClass: Class<out Event>
            get() = when (this) {
                EventType.NO_SUBSCRIPTIONS -> NoSubscriptionsEvent::class.java
                EventType.THREE_SUBSCRIPTIONS -> ThreeSubscriptionsEvent::class.java
                EventType.FIVE_SUBSCRIPTIONS -> FiveSubscriptionsEvent::class.java
            }

        private inline fun <E : TestEvent> TestEventStore<E>.subscribedTestActions() = simpleActions.map { it as LocalTestEventAction<E> }

        private val EventType.declaredEventActions: Sequence<LocalTestEventAction<*>>
            get() = eventClass.declaredActions()
        //endregion
    }

    //region Other Classes
    enum class EventType {NO_SUBSCRIPTIONS, THREE_SUBSCRIPTIONS, FIVE_SUBSCRIPTIONS }

    class ProducedValueConverter : ParameterConverterBase<LocalTestEventAction.ProducedValue>(LocalTestEventAction.ProducedValue::class.java) {
        private companion object {
            //a1:3
            val VALUE_PATTERN = Regex("a(\\d+):(\\d+)", RegexOption.IGNORE_CASE)
        }

        override fun convertValue(value: String, type: Type): LocalTestEventAction.ProducedValue {
            try {
                with(VALUE_PATTERN.matchEntire(value)!!) {
                    return LocalTestEventAction.ProducedValue(
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