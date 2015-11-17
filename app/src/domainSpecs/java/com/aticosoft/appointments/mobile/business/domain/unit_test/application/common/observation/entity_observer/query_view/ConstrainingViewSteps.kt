package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.AbstractConstrainingViewSteps.Services
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.*
import com.rodrigodev.common.rx.advanceTimeBy
import com.rodrigodev.common.spec.story.SpecSteps
import com.rodrigodev.common.spec.story.converter.JsonData
import com.rodrigodev.common.testing.firstEvent
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import javax.inject.Inject
import javax.jdo.JDODetachedFieldAccessException
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 19/11/15.
 */
internal abstract class AbstractConstrainingViewSteps(protected val s: Services) : SpecSteps() {

    protected var queryView by notNull<TestDataParentQueryView>()
    protected var filter: EntityObservationFilter<*>? = null

    @Given("data:\$values")
    fun givenData(values: MutableList<TestDataParentExample>) {
        s.testDataParentRepositoryManager.clear()
        values.forEach { parent ->
            s.testDataParentServices.execute(TestDataParentServices.AddData(
                    parent.v,
                    parent.c1?.let { c -> TestDataParentServices.AddData.Child(c.v, c.g1, c.g2) },
                    parent.c2?.let { c -> TestDataParentServices.AddData.Child(c.v, c.g1, c.g2) }
            ))
        }
    }

    @Given("I'm using the query view \$query_view")
    fun givenImUsingTheQueryView(queryView: TestDataParentQueryView) {
        this.queryView = queryView
    }

    @When("I increment by 1 the values of all \$path")
    fun whenIIncrementBy1TheValueOf(path: String) {
        when (path) {
            "child1" -> s.testDataParentServices.execute(TestDataParentServices.IncrementAllChild1())
            "child2.grandchild1" -> s.testDataParentServices.execute(TestDataParentServices.IncrementAllChild2GrandChild1())
        }
    }

    protected fun advanceTime() {
        s.testScheduler.advanceTimeBy(EntityObserver.DATA_REFRESH_RATE_TIME)
    }

    @Given("dummy")
    fun givenDummy() {
    }

    class Services @Inject constructor(
            val testDataParentRepositoryManager: TestDataParentRepositoryManager,
            val testDataParentServices: TestDataParentServices,
            val testDataParentObserver: TestDataParentObserver,
            val testDataParentQueries: TestDataParentQueries,
            val testScheduler: TestScheduler
    )
}

internal abstract class ConstrainingViewOfSingleEntitySteps(s: Services) : AbstractConstrainingViewSteps(s) {

    private var testSubscriber by notNull<TestSubscriber<TestDataParent?>>()

    @Given("a parent-only filter that filters parents with value \$parent is being used")
    fun givenAParentOnlyFilterIsBeingUsed(value: Int) {
        useAParentOnlyFilter(value)
    }

    protected abstract fun useAParentOnlyFilter(value: Int)

    @When("I'm observing the parent with value \$value")
    fun whenImObservingTheParentWithValue(value: Int) {
        testSubscriber = observeTheParentWithValue(value)
    }

    protected abstract fun observeTheParentWithValue(value: Int): TestSubscriber<TestDataParent?>

    @Then("the observed value is \$result")
    fun thenTheObservedValueIs(result: TestDataParentExample) {
        assertThat(testSubscriber.firstEvent()?.toExample()).isEqualTo(result)
    }

    @Then("later the observed values were \$result")
    fun thenLaterTheObservedValuesWere(result: MutableList<TestDataParentExample>) {
        advanceTime()
        assertThat(testSubscriber.onNextEvents.map { it?.toExample() })
                .containsExactlyElementsOf(result)
    }
}

private fun TestDataParent.toExample() = TestDataParentExample(
        value,
        readField { child1 }?.let { c -> TestDataChildExample(c.value, readField { c.grandChild1 }?.value, readField { c.grandChild2 }?.value) },
        readField { child2 }?.let { c -> TestDataChildExample(c.value, readField { c.grandChild1 }?.value, readField { c.grandChild2 }?.value) }
)

private inline fun <T : Any> readField(body: () -> T?): T? = try {
    body()
}
catch(e: JDODetachedFieldAccessException) {
    null
}

@JsonData
internal data class TestDataParentExample(
        val v: Int,
        val c1: TestDataChildExample?,
        val c2: TestDataChildExample?
)

internal data class TestDataChildExample(
        val v: Int,
        val g1: Int?,
        val g2: Int?
)