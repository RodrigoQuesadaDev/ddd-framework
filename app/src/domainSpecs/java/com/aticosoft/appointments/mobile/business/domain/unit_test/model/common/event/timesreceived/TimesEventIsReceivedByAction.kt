@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.timesreceived

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStore
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.EventActionStepBase
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.timesreceived.TimesEventIsReceivedByAction.LocalSteps.LocalEventType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.timesreceived.TimesEventIsReceivedByAction.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.timesreceived.test_data.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.timesreceived.test_data.LocalProducedValue.TimesReceivedType
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
 * Created by Rodrigo Quesada on 16/10/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class TimesEventIsReceivedByAction : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<TimesEventIsReceivedByAction>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val sampleEventMembers: LocalEventMembers<SampleEvent, SampleEventServices>
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

        //region Event Members
        @Singleton
        class LocalEventMembers<E : TestEvent, out S : TestEventServices<E>> @Inject protected constructor(
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
        }

        data class EventActionDefinition(val type: TimesReceivedType, val priority: Int)

        private inline fun EventAction<*>.toEventActionDefinition() = EventActionDefinition(TimesReceivedType.from(this), priority)

        private inline fun Sequence<EventAction<*>>.toEventActionDefinitions() = map { it.toEventActionDefinition() }
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
                                TimesReceivedType.from(groups[1]!!.value),
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
                                TimesReceivedType.from(groups[1]!!.value),
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