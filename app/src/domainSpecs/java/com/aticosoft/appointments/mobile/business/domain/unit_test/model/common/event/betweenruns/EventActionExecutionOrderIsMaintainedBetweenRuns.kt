@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.betweenruns

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventActionsManagerImpl
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStore
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.betweenruns.EventActionExecutionOrderIsMaintainedBetweenRuns.LocalSteps.LocalEventType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.betweenruns.EventActionExecutionOrderIsMaintainedBetweenRuns.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.betweenruns.test_data.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.EventActionStepBase
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.rodrigodev.common.assertj.Assertions.assertThatList
import com.rodrigodev.common.spec.story.converter.ParameterConverterBase
import org.jbehave.core.annotations.Given
import org.jbehave.core.steps.ParameterConverters.ParameterConverter
import org.jbehave.core.steps.ParameterConverters.ParameterConvertionFailed
import org.robolectric.annotation.Config
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 18/10/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class EventActionExecutionOrderIsMaintainedBetweenRuns : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<EventActionExecutionOrderIsMaintainedBetweenRuns>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val sampleEventMembers: LocalEventMembers<SampleEvent, SampleEventServices>,
            private val eventActionsManager: TestEventActionsManagerImpl
    ) : EventActionStepBase<LocalSteps, LocalEventType, LocalTestEventAction<*>>() {

        override val eventTypeValues = LocalEventType.values()
        override val actionType = LocalTestEventAction::class.java

        override val converters: Array<ParameterConverter> = arrayOf(ProducedValueConverter(), EventActionDefinitionConverter())

        @Given("\$eventType actions are defined like: [\$definitions]")
        fun givenSampleActionsAreDefinedLike(eventType: LocalEventType, definitions: MutableList<EventActionDefinition>) {
            assertThatList(eventType.declaredEventActions.toEventActionDefinitions().toList())
                    .containsExactlyElementsOfInAnyOrder(definitions)
        }

        @Given("action events order is randomized by EventActionsManager")
        fun givenActionEventsOrderIsRandomizedByEventActionsManager() {
            eventActionsManager.randomizeEventActionsOrder()
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
            SAMPLE(SampleEvent::class.java, { sampleEventMembers })
        }

        data class EventActionDefinition(val id: Int, val priority: Int)

        private inline fun LocalTestEventAction<*>.toEventActionDefinition() = EventActionDefinition(id, priority)

        private inline fun Sequence<LocalTestEventAction<*>>.toEventActionDefinitions() = map { it.toEventActionDefinition() }
        //endregion

        //region Converters
        class ProducedValueConverter : ParameterConverterBase<LocalProducedValue>(LocalProducedValue::class.java) {
            private companion object {
                val VALUE_PATTERN = Regex("id(\\d+):(\\d+)", RegexOption.IGNORE_CASE)
            }

            override fun convertValue(value: String, type: Type): LocalProducedValue {
                try {
                    with(VALUE_PATTERN.matchEntire(value)!!) {
                        return LocalProducedValue(
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

        class EventActionDefinitionConverter : ParameterConverterBase<EventActionDefinition>(EventActionDefinition::class.java) {
            private companion object {
                val VALUE_PATTERN = Regex("id(\\d+):p(\\d+)", RegexOption.IGNORE_CASE)
            }

            override fun convertValue(value: String, type: Type): EventActionDefinition {
                try {
                    with(VALUE_PATTERN.matchEntire(value)!!) {
                        return EventActionDefinition(
                                groups[1]!!.value.toInt(),
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