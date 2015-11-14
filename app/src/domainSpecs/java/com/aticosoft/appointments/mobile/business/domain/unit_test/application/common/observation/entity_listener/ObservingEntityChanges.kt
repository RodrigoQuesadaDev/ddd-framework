package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataListener
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.*
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.ObservingEntityChanges.TestApplicationImpl
import com.rodrigodev.common.spec.story.SpecSteps
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
@Config(application = TestApplicationImpl::class)
internal class ObservingEntityChanges : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<ObservingEntityChanges>

    class TestApplicationImpl : TestApplication(DaggerObservingEntityChanges_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val testDataListener: TestDataListener,
            private val testDataRepositoryManager: TestDataRepositoryManager,
            private val testDataServices: TestDataServices,
            private val testScheduler: TestScheduler
    ) : SpecSteps() {

        private var testSubscriber: TestSubscriber<EntityChangeEvent<TestData>> by notNull()

        @Given("no data")
        fun givenNoData() {
            testDataRepositoryManager.clear()
        }

        @Given("I subscribe to the EntityListener")
        fun givenISubscribeToTheEntityListener() {
            testSubscriber = testDataListener.changes.testSubscribe()
        }

        @When("{after that |}I insert [\$values]")
        fun whenAfterThatIInsert(values: MutableList<Int>) {
            values.forEach { testDataServices.execute(AddData(it)) }
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

private fun EntityChangeEvent<TestData>.toExample() = ChangeEventExample(type, previous = previousValue?.value, current = currentValue?.value)

@AsParameters
private data class ChangeEventExample(
        var type: EntityChangeEvent.EventType?,
        var previous: Int?,
        var current: Int?
) {
    constructor() : this(null, null, null)
}