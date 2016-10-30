/*
@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.garbage.actionbehavior

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.isEmpty
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEventStore
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.EventActionStepBase
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.garbage.actionbehavior.EventActionBehavior.LocalSteps.LocalEventType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.garbage.actionbehavior.EventActionBehavior.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.actionbehavior.test_data.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.garbage.actionbehavior.test_data.*
import com.rodrigodev.common.spec.story.converter.ParameterConverterBase
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.steps.ParameterConverters.ParameterConverter
import org.jbehave.core.steps.ParameterConverters.ParameterConvertionFailed
import org.robolectric.annotation.Config
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

*/
/**
 * Created by Rodrigo Quesada on 11/08/16.
 *//*

@Config(application = UnitTestApplicationImpl::class)
internal class EventActionBehavior : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<EventActionBehavior>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val noActionsEventMembers: LocalEventMembers<NoActionsEvent, NoActionsEventServices>,
            private val threeActionsEventMembers: LocalEventMembers<ThreeActionsEvent, ThreeActionsEventServices>,
            private val fiveActionsEventMembers: LocalEventMembers<FiveActionsEvent, FiveActionsEventServices>
    ) : EventActionStepBase<LocalSteps, LocalEventType, LocalTestEventAction<*>>() {

        override val eventTypeValues = LocalEventType.values()
        override val actionType = LocalTestEventAction::class.java

        override val converters: Array<ParameterConverter> = arrayOf(ProducedValueConverter())

        @Given("event \$eventType has \$n actions defined")
        fun givenEventHasNActionsDefined(eventType: LocalEventType, n: Int) {
            assertThat(eventType.declaredEventActions.toList()).hasSize(n)
        }

        @Then("\$eventType actions don't get triggered")
        fun thenLocalEventTypeActionsDontGetTriggered(eventType: LocalEventType) = with(eventType) {
            assertThat(m.valueProducer.producedValues).isEmpty()
        }

        @Then("there are no \$eventType events left")
        fun thenThereAreNoEventsLeft(eventType: LocalEventType) = with(eventType) {
            assertThat(m.repositoryManager.repository { isEmpty() }).isTrue()
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
            NO_ACTIONS(NoActionsEvent::class.java, { noActionsEventMembers }),
            THREE_ACTIONS(ThreeActionsEvent::class.java, { threeActionsEventMembers }),
            FIVE_ACTIONS(FiveActionsEvent::class.java, { fiveActionsEventMembers })
        }

        class ProducedValueConverter : ParameterConverterBase<LocalProducedValue>(LocalProducedValue::class.java) {
            private companion object {
                val VALUE_PATTERN = Regex("a(\\d+):(\\d+)", RegexOption.IGNORE_CASE)
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
}*/
