@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.listener.async

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectChangeEvent
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListenersManager
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEntityRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.listener.async.EntityType.CHILD
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.listener.async.EntityType.PARENT
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.listener.async.ObservingObjectChangesAsync.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.listener.async.test_data.TestDataChild
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.listener.async.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.listener.async.test_data.TestDataParentServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.listener.async.test_data.TestDataParentServices.*
import com.rodrigodev.common.rx.testing.testSubscribe
import com.rodrigodev.common.spec.story.steps.SpecSteps
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
internal class ObservingObjectChangesAsync : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<ObservingObjectChangesAsync>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            persistableObjectListenersManager: PersistableObjectAsyncListenersManager,
            private val testDataRepositoryManager: TestEntityRepositoryManager<TestDataParent>,
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

private inline fun PersistableObjectChangeEvent<AbstractTestData>.toExample() = ChangeEventExample(type, previous = previousValue?.value, current = currentValue?.value)

@AsParameters
internal data class ChangeEventExample(
        var type: PersistableObjectChangeEvent.EventType?,
        var previous: Int?,
        var current: Int?
) {
    constructor() : this(null, null, null)
}

internal enum class EntityType {PARENT, CHILD }