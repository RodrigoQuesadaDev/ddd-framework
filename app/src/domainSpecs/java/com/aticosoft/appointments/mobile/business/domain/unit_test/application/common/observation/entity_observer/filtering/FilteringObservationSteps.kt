package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.AbstractFilteringObservationSteps.Services
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentServices.ChangeData
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentServices.RemoveData
import com.rodrigodev.common.rx.advanceTimeBy
import com.rodrigodev.common.spec.story.SpecSteps
import com.rodrigodev.common.spec.story.converter.JsonData
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 11/11/15.
 */
internal abstract class AbstractFilteringObservationSteps(private val s: Services) : SpecSteps() {

    @Given("no data")
    fun givenNoData() {
        s.testDataParentRepositoryManager.clear()
    }

    @Given("data \$values")
    fun givenData(values: MutableList<TestDataParentExample>) {
        s.testDataParentRepositoryManager.clear()
        addValues(values)
    }

    @When("later I add \$values")
    fun whenLaterIAdd(values: MutableList<TestDataParentExample>) {
        advanceTime()
        addValues(values)
    }

    @When("later I remove parent{s|} [\$values]")
    fun whenLaterIRemoveParents(values: MutableList<Int>) {
        advanceTime()
        values.forEach { s.testDataParentServices.execute(RemoveData(it)) }
    }

    @When("later I change child of parent \$parent to \$newChildValue")
    fun whenLaterIChangeChildOfParentTo(parent: Int, newChildValue: Int) {
        advanceTime()
        s.testDataParentServices.execute(TestDataParentServices.ChangeChild(parent, newChildValue))
    }

    @When("later I change parent \$currentValue to \$targetValue")
    fun whenLaterIChangeParentValueTo(currentValue: Int, targetValue: Int) {
        advanceTime()
        s.testDataParentServices.execute(ChangeData(currentValue, targetValue))
    }

    protected fun advanceTime() {
        s.testScheduler.advanceTimeBy(EntityObserver.DATA_REFRESH_RATE_TIME)
    }

    private fun addValues(values: MutableList<TestDataParentExample>) {
        values.forEach { s.testDataParentServices.execute(TestDataParentServices.AddData(it.p, it.c)) }
    }

    class Services @Inject protected constructor(
            val testDataParentRepositoryManager: TestDataParentRepositoryManager,
            val testDataParentServices: TestDataParentServices,
            val testScheduler: TestScheduler
    )
}

internal abstract class FilteringObservationUniqueEntitySteps(services: Services) : AbstractFilteringObservationSteps(services) {

    private lateinit var testSubscriber: TestSubscriber<TestDataParent?>

    @Given("I'm observing parent entity with value \$value")
    fun givenImObservingParentEntityWithValue(value: Int) {
        testSubscriber = observeTheParentEntityWithValue(value)
    }

    protected abstract fun observeTheParentEntityWithValue(value: Int): TestSubscriber<TestDataParent?>

    @Then("later the values observed were \$result")
    fun thenLaterTheValuesObservedWere(result: MutableList<TestDataParentExample>) {
        advanceTime()
        assertThat(testSubscriber.onNextEvents.map { it?.toExample() })
                .containsExactlyElementsOf(result)
    }
}

internal fun TestDataParent.toExample() = TestDataParentExample(value, child.value)

@JsonData
internal data class TestDataParentExample(
        val p: Int,
        val c: Int
)