package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectListenersManager
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.EntityType.CHILD
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.EntityType.PARENT
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.ObservingEntityChanges.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data.TestDataChild
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data.TestDataParentServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data.TestDataParentServices.*
import com.rodrigodev.common.spec.story.steps.SpecSteps
import com.rodrigodev.common.testing.testSubscribe
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class ObservingEntityChanges : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<ObservingEntityChanges>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val persistableObjectListenersManager: PersistableObjectListenersManager,
            private val testDataRepositoryManager: TestRepositoryManager<TestDataParent>,
            private val testDataServices: TestDataParentServices,
            private val testScheduler: TestScheduler
    ) : SpecSteps() {

        private val testDataParentListener = persistableObjectListenersManager.forType(TestDataParent::class.java)
        private val testDataChildListener = persistableObjectListenersManager.forType(TestDataChild::class.java)
        private lateinit var testSubscriber: TestSubscriber<out PersistableObjectChangeEvent<AbstractTestData>>

        @Given("no data")
        fun givenNoData() {
            testDataRepositoryManager.clear()
        }

        @Given("I subscribe to the EntityListener of a \$entityType entity")
        fun givenISubscribeToTheEntityListener(entityType: EntityType) {
            testSubscriber = when (entityType) {
                PARENT -> testDataParentListener.changes.testSubscribe()
                CHILD -> testDataChildListener.changes.testSubscribe()
            }
        }

        @When("{after that |}I insert [\$values]")
        fun whenAfterThatIInsert(values: MutableList<Int>) {
            values.forEach { testDataServices.execute(AddData(it, it)) }
        }

        @When("{after that |}I delete [\$values]")
        fun whenAfterThatIDelete(values: MutableList<Int>) {
            values.forEach { testDataServices.execute(RemoveData(it)) }
        }

        @When("{after that |}I change \$currentValue into \$targetValue")
        fun whenAfterThatIChangeAValueInto(currentValue: Int, targetValue: Int) {
            testDataServices.execute(ChangeData(currentValue, targetValue))
        }

        @Then("the entity change events observed were \$events")
        fun thenTheEntityChangeEventsObservedWere(events: MutableList<ChangeEventExample>) {
            testScheduler.triggerActions()
            assertThat(testSubscriber.onNextEvents.map { it.toExample() }).containsExactlyElementsOf(events)
        }
    }
}

private fun PersistableObjectChangeEvent<AbstractTestData>.toExample() = ChangeEventExample(type, previous = previousValue?.value, current = currentValue?.value)

@AsParameters
internal data class ChangeEventExample(
        var type: PersistableObjectChangeEvent.EventType?,
        var previous: Int?,
        var current: Int?
) {
    constructor() : this(null, null, null)
}

internal enum class EntityType {PARENT, CHILD }