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
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.EventActionStepBase
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data.TestEventServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.SimpleEventActionBehavior.LocalSteps.LocalEventType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.SimpleEventActionBehavior.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data.*
import com.rodrigodev.common.spec.story.converter.ParameterConverterBase
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
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
internal class SimpleEventActionBehavior : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<SimpleEventActionBehavior>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: SimpleEventActionBehavior.LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val noSubscriptionsEventMembers: LocalEventMembers<NoSubscriptionsEvent, NoSubscriptionsEventServices>,
            private val threeSubscriptionsEventMembers: LocalEventMembers<ThreeSubscriptionsEvent, ThreeSubscriptionsEventServices>,
            private val fiveSubscriptionsEventMembers: LocalEventMembers<FiveSubscriptionsEvent, FiveSubscriptionsEventServices>
    ) : EventActionStepBase<LocalSteps, LocalEventType, LocalTestEventAction<*>>() {

        override val eventTypeValues = LocalEventType.values()
        override val actionType = LocalTestEventAction::class.java

        override val converters: Array<ParameterConverters.ParameterConverter> = arrayOf(ProducedValueConverter())

        @Given("event \$eventType has \$n actions subscribed")
        fun givenEventHasNActionsSubscribed(eventType: LocalEventType, n: Int) {
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
            NO_SUBSCRIPTIONS(NoSubscriptionsEvent::class.java, { noSubscriptionsEventMembers }),
            THREE_SUBSCRIPTIONS(ThreeSubscriptionsEvent::class.java, { threeSubscriptionsEventMembers }),
            FIVE_SUBSCRIPTIONS(FiveSubscriptionsEvent::class.java, { fiveSubscriptionsEventMembers })
        }

        class ProducedValueConverter : ParameterConverterBase<LocalProducedValue>(LocalProducedValue::class.java) {
            private companion object {
                //a1:3
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
}