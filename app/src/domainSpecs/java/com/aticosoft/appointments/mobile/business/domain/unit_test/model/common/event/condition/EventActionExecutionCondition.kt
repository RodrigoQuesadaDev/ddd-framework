@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.condition

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStore
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.EventActionStepBase
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.condition.EventActionExecutionCondition.LocalSteps.LocalEventType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.condition.EventActionExecutionCondition.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.condition.test_data.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.condition.test_data.LocalProducedValue.ExecutionConditionType
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.event.exceptions.IllegallyModifiedEvent
import com.rodrigodev.common.assertj.Assertions.assertThatList
import com.rodrigodev.common.spec.story.converter.ParameterConverterBase
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.jbehave.core.steps.ParameterConverters.ParameterConverter
import org.jbehave.core.steps.ParameterConverters.ParameterConvertionFailed
import org.robolectric.annotation.Config
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 16/10/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class EventActionExecutionCondition : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<EventActionExecutionCondition>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val sampleEventMembers: LocalEventMembers<SampleEvent, SampleEventServices>,
            private val badCondModifiesEventMembers: LocalEventMembers<BadCondModifiesEvent, BadCondModifiesEventServices>,
            private val badCondAddedAfterInitEventMembers: LocalEventMembers<BadCondAddedAfterInitEvent, BadCondAddedAfterInitEventServices>
    ) : EventActionStepBase<LocalSteps, LocalEventType, LocalTestEventAction<*>>() {

        override val eventTypeValues = LocalEventType.values()
        override val actionType = LocalTestEventAction::class.java

        override val converters: Array<ParameterConverter> = arrayOf(ProducedValueConverter(), EventActionDefinitionConverter())

        @Given("\$eventType actions are defined like: [\$definitions]")
        fun givenSampleActionsAreDefinedLike(eventType: LocalEventType, definitions: MutableList<EventActionDefinition>) {
            assertThatList(
                    eventType.declaredEventActions.toEventActionDefinitions().toList()
            )
                    .containsExactlyElementsOfInAnyOrder(definitions)
        }

        @Given("\$eventType action modifies the event during condition evaluation")
        fun givenBadCondModifiesActionModifiesTheEventDuringConditionEvaluation(eventType: LocalEventType) {
            assertThat(eventType.declaredEventActions.single().condition).isInstanceOf(BadModifyingCondition::class.java)
        }

        @When("condition for \$eventType action is added after it's been initialized")
        fun whenConditionForActionIsAddedAfterItHasBeenInitialized(eventType: LocalEventType) = with(eventType) {
            mightThrowException { m.eventStoreManager.simpleLocalActions.single().addCondition() }
        }

        @Then("the system throws an exception indicating an action illegally modified the event")
        fun thenTheSystemThrowsAnExceptionIndicatingAnActionIllegallyModifiedTheEvent() {
            assertThat(thrownException).isInstanceOf(IllegallyModifiedEvent::class.java)
        }

        @Then("the system throws an exception indicating a condition was added to the action after its initialization")
        fun thenTheSystemThrowsAnExceptionIndicatingAConditionWasAddedToTheActionAfterItsInitialization() {
            assertThat(thrownException).isInstanceOf(IllegalStateException::class.java)
        }

        //region Event Members
        @Singleton
        class LocalEventMembers<E : TestEvent, out S : TestEventServices<E>> @Inject protected constructor(
                val repositoryManager: TestEventRepositoryManager<E>,
                override val eventStoreManager: TestEventStore<E>,
                override val services: S,
                override val valueProducer: LocalValueProducer<E>
        ) : EventMembers<E, S>
        //endregion

        //region Other Classes
        enum class LocalEventType(
                override val eventClass: Class<out Event>,
                override val eventMembers: LocalSteps.() -> LocalEventMembers<*, *>
        ) : EventType<LocalSteps, LocalEventMembers<*, *>> {
            SAMPLE(SampleEvent::class.java, { sampleEventMembers }),
            BAD_COND_MODIFIES(BadCondModifiesEvent::class.java, { badCondModifiesEventMembers }),
            BAD_COND_ADDED_AFTER_INIT(BadCondAddedAfterInitEvent::class.java, { badCondAddedAfterInitEventMembers }),
        }

        data class EventActionDefinition(val conditionType: ExecutionConditionType, val priority: Int)

        private inline fun LocalTestEventAction<*>.toEventActionDefinition() = EventActionDefinition(ExecutionConditionType.from(this), priority)

        private inline fun Sequence<LocalTestEventAction<*>>.toEventActionDefinitions() = map { it.toEventActionDefinition() }
        //endregion

        //region Converters
        class ProducedValueConverter : ParameterConverterBase<LocalProducedValue>(LocalProducedValue::class.java) {
            private companion object {
                val VALUE_PATTERN = Regex("(\\w):(\\d+)", RegexOption.IGNORE_CASE)
            }

            override fun convertValue(value: String, type: Type): LocalProducedValue {
                try {
                    with(VALUE_PATTERN.matchEntire(value)!!) {
                        return LocalProducedValue(
                                ExecutionConditionType.from(groups[1]!!.value),
                                groups[2]!!.value.toInt()
                        )
                    }
                }
                catch(e: Exception) {
                    throw ParameterConvertionFailed("Unable to convert value to ProducedValue.", e)
                }
            }
        }

        class EventActionDefinitionConverter : ParameterConverterBase<EventActionDefinition>(EventActionDefinition::class.java) {
            private companion object {
                val VALUE_PATTERN = Regex("(\\w):p(\\d+)", RegexOption.IGNORE_CASE)
            }

            override fun convertValue(value: String, type: Type): EventActionDefinition {
                try {
                    with(VALUE_PATTERN.matchEntire(value)!!) {
                        return EventActionDefinition(
                                ExecutionConditionType.from(groups[1]!!.value),
                                groups[2]!!.value.toInt()
                        )
                    }
                }
                catch(e: Exception) {
                    throw ParameterConvertionFailed("Unable to convert value to EventActionDefinition.", e)
                }
            }
        }
        //endregion
    }
}