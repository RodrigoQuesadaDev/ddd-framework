@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStore
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.EventActionStepBase
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.EventActionPriority.LocalSteps.LocalEventType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.EventActionPriority.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.test_data.*
import com.rodrigodev.common.assertj.Assertions.assertThatList
import com.rodrigodev.common.kotlin.nullable
import com.rodrigodev.common.spec.story.converter.ParameterConverterBase
import org.jbehave.core.annotations.Given
import org.jbehave.core.steps.ParameterConverters.ParameterConverter
import org.jbehave.core.steps.ParameterConverters.ParameterConvertionFailed
import org.robolectric.annotation.Config
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 11/10/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class EventActionPriority : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<EventActionPriority>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: EventActionPriority.LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val samePriorityEventMembers: LocalEventMembers<SamePriorityEvent, SamePriorityEventServices>,
            private val differentPriorityEventMembers: LocalEventMembers<DifferentPriorityEvent, DifferentPriorityEventServices>,
            private val defaultPriorityEventMembers: LocalEventMembers<DefaultPriorityEvent, DefaultPriorityEventServices>
    ) : EventActionStepBase<LocalSteps, LocalEventType, LocalTestEventAction<*>>() {

        override val eventTypeValues = LocalEventType.values()
        override val actionType = LocalTestEventAction::class.java

        override val converters: Array<ParameterConverter> = arrayOf(ProducedValueConverter())

        @Given("\$eventType actions have priority values: [\$values]")
        fun givenSamePriorityActionsHavePriorityValues(eventType: LocalEventType, values: MutableList<Int?>) {
            assertThatList(eventType.declaredEventActions.map { it.declaredPriority() }.toList())
                    .containsExactlyElementsOfInAnyOrder(values)
        }

        //region Event Members
        @Singleton
        class LocalEventMembers<E : TestEvent, out S : TestEventServices<E>> @Inject protected constructor(
                val repository: EventRepository<E>,
                override val eventStoreManager: TestEventStore<E>,
                override val services: S,
                override val valueProducer: LocalValueProducer<E>
        ) : EventMembers<E, S>
        //endregion

        //region Utils
        private fun LocalTestEventAction<*>.declaredPriority()
                : Int? = javaClass.nullable { getDeclaredField("priority") }
                ?.apply { isAccessible = true }
                ?.getInt(this)
        //endregion

        //region Other Classes
        enum class LocalEventType(
                override val eventClass: Class<out Event>,
                override val eventMembers: LocalSteps.() -> LocalEventMembers<*, *>
        ) : EventType<LocalSteps, LocalEventMembers<*, *>> {
            SAME_PRIORITY(SamePriorityEvent::class.java, { samePriorityEventMembers }),
            DIFFERENT_PRIORITY(DifferentPriorityEvent::class.java, { differentPriorityEventMembers }),
            DEFAULT_PRIORITY(DefaultPriorityEvent::class.java, { defaultPriorityEventMembers })
        }

        class ProducedValueConverter : ParameterConverterBase<LocalProducedValue>(LocalProducedValue::class.java) {
            private companion object {
                val VALUE_PATTERN = Regex("a(-?\\d+):(\\d+)", RegexOption.IGNORE_CASE)
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
        //endregion
    }
}