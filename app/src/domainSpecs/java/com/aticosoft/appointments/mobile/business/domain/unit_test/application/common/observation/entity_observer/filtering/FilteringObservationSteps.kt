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
import org.assertj.core.api.Assertions
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import javax.inject.Inject
import kotlin.properties.Delegates.notNull

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

    protected class Services @Inject constructor(
            val testDataParentRepositoryManager: TestDataParentRepositoryManager,
            val testDataParentServices: TestDataParentServices,
            val testScheduler: TestScheduler
    )
}

internal class FilteringObservationUniqueEntitySteps @Inject constructor(services: Services) : AbstractFilteringObservationSteps(services) {

    var testSubscriber: TestSubscriber<TestDataParent?> by notNull()

    @Then("later the values observed were \$result")
    fun thenLaterTheValuesObservedWere(result: MutableList<TestDataParentExample>) {
        advanceTime()
        Assertions.assertThat(testSubscriber.onNextEvents.map { it?.toExample() })
                .containsExactlyElementsOf(result)
    }
}

internal class FilteringObservationEntityListSteps @Inject constructor(services: Services) : AbstractFilteringObservationSteps(services) {

    var testSubscriber: TestSubscriber<List<TestDataParent>> by notNull()

    @Then("later the values observed were \$result")
    fun thenLaterTheValuesObservedWere(result: MutableList<MutableList<TestDataParentExample>>) {
        advanceTime()
        Assertions.assertThat(testSubscriber.onNextEvents.map { list -> list.map(TestDataParent::toExample) })
                .containsExactlyElementsOf(result)
    }
}

internal class FilteringObservationEntityCountSteps @Inject constructor(services: Services) : AbstractFilteringObservationSteps(services) {

    var testSubscriber: TestSubscriber<Long> by notNull()

    @Then("later the values observed were \$result")
    fun thenLaterTheValuesObservedWere(result: MutableList<Long>) {
        advanceTime()
        Assertions.assertThat(testSubscriber.onNextEvents).containsExactlyElementsOf(result)
    }
}

private fun TestDataParent.toExample() = TestDataParentExample(value, child.value)

@JsonData
internal data class TestDataParentExample(
        val p: Int,
        val c: Int
)