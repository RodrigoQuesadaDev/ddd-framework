@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices.AddEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.declaredActions
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.EventActionPriority.EventType.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.EventActionPriority.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.test_data.*
import com.rodrigodev.common.kotlin.nullable
import com.rodrigodev.common.spec.story.converter.ParameterConverterBase
import com.rodrigodev.common.spec.story.steps.SpecSteps
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.*
import org.jbehave.core.steps.ParameterConverters
import org.jbehave.core.steps.ParameterConverters.ParameterConvertionFailed
import org.robolectric.annotation.Config
import java.lang.reflect.Type
import java.util.*
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
            private val samePriorityEventMembers: EventMembers<SamePriorityEvent, SamePriorityEventServices>,
            private val differentPriorityEventMembers: EventMembers<DifferentPriorityEvent, DifferentPriorityEventServices>,
            private val defaultPriorityEventMembers: EventMembers<DefaultPriorityEvent, DefaultPriorityEventServices>
    ) : SpecSteps() {

        override val converters: Array<ParameterConverters.ParameterConverter> = arrayOf(ProducedValueConverter())

        @BeforeScenario(uponType = ScenarioType.ANY)
        fun clearValues() {
            EventType.values().forEach { it.m.valueProducer.clear() }
        }

        @Given("\$eventType actions have priority values: [\$values]")
        fun givenSamePriorityActionsHavePriorityValues(eventType: EventType, values: MutableList<Int?>) {
            assertThat(eventType.declaredEventActions
                    .map { it.declaredPriority() }
                    .sortedWith(PriorityComparator)
                    .toList()
            )
                    .containsExactlyElementsOf(values.sortedWith(PriorityComparator))
        }

        @When("event \$eventType occurs with value \$value")
        fun whenAnEventOccursWithValue(eventType: EventType, value: Int) {
            eventType.m.services.execute(AddEvent(value))
        }

        @Then("\$eventType actions produce the next values in order: [\$values]")
        fun thenActionsProduceTheNextValuesInOrder(eventType: EventType, values: MutableList<LocalTestEventAction.ProducedValue>) {
            assertThat(eventType.m.valueProducer.producedValues).containsExactlyElementsOf(values)
        }

        //region Event Members
        @Singleton
        class EventMembers<E : TestEvent, out S : TestEventServices<E>> @Inject protected constructor(
                val repository: EventRepository<E>,
                val services: S,
                val valueProducer: LocalTestEventAction.ValueProducer<E>
        )
        //endregion

        //region Utils
        private val EventType.m: EventMembers<*, *>
            get() = when (this) {
            //TODO trying to import EventType enums causes: Recursion detected on input: IMPORT_DIRECTIVE... error
                SAME_PRIORITY -> samePriorityEventMembers
                DIFFERENT_PRIORITY -> differentPriorityEventMembers
                DEFAULT_PRIORITY -> defaultPriorityEventMembers
            }

        private val EventType.eventClass: Class<out Event>
            get() = when (this) {
                SAME_PRIORITY -> SamePriorityEvent::class.java
                DIFFERENT_PRIORITY -> DifferentPriorityEvent::class.java
                DEFAULT_PRIORITY -> DefaultPriorityEvent::class.java
            }

        private val EventType.declaredEventActions: Sequence<LocalTestEventAction<*>>
            get() = eventClass.declaredActions()

        private fun LocalTestEventAction<*>.declaredPriority()
                : Int? = javaClass.nullable { getDeclaredField("priority") }
                ?.apply { isAccessible = true }
                ?.getInt(this)
        //endregion
    }

    //region Other Classes
    enum class EventType {SAME_PRIORITY, DIFFERENT_PRIORITY, DEFAULT_PRIORITY }

    class ProducedValueConverter : ParameterConverterBase<LocalTestEventAction.ProducedValue>(LocalTestEventAction.ProducedValue::class.java) {
        private companion object {
            //a1:3
            val VALUE_PATTERN = Regex("a(-?\\d+):(\\d+)", RegexOption.IGNORE_CASE)
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

    object PriorityComparator : Comparator<Int?> {

        override fun compare(lhs: Int?, rhs: Int?): Int {
            return when {
                lhs == rhs -> 0
                lhs == null -> -1
                rhs == null -> 1
                else -> lhs.compareTo(rhs)
            }
        }
    }
    //endregion
}