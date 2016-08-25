package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectObservationFilter
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectObserver
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParentServices.AddData
import com.rodrigodev.common.nullability.nullOr
import com.rodrigodev.common.rx.advanceTimeBy
import com.rodrigodev.common.spec.story.converter.JsonData
import com.rodrigodev.common.spec.story.steps.SpecSteps
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
    protected var filter: PersistableObjectObservationFilter<*>? = null

    @Given("data:\$values")
    fun givenData(values: MutableList<TestDataParentExample>) {
        //region Utils
        fun createComplexEmbedded(e: TestDataEmbeddedExample?) = e.nullOr { AddData.ComplexEmbedded(it.v, it.n) }

        fun createGrandChild(g: TestDataGrandChildExample?) = g.nullOr { AddData.GrandChild(it.v, it.e1, createComplexEmbedded(it.e2)) }

        fun createChild(c: TestDataChildExample?) = c.nullOr { AddData.Child(it.v, createGrandChild(it.g1), createGrandChild(it.g2)) }
        //endregion

        s.testDataParentRepositoryManager.clear()
        values.forEach { parent ->
            s.testDataParentServices.execute(AddData(
                    parent.v,
                    parent.e1,
                    createComplexEmbedded(parent.e2),
                    createChild(parent.c1),
                    createChild(parent.c2)
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
        s.testScheduler.advanceTimeBy(PersistableObjectObserver.DATA_REFRESH_RATE_TIME)
    }

    @Given("dummy")
    fun givenDummy() {
    }

    class Services @Inject constructor(
            val testDataParentRepositoryManager: TestRepositoryManager<TestDataParent>,
            val testDataParentServices: TestDataParentServices,
            val testDataParentObserver: EntityObserver<TestDataParent>,
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

//region Examples
internal fun TestDataParent.toExample(): TestDataParentExample {

    //region Utils
    fun <F : Any, R : Any> readField(fieldClosure: () -> F?, body: (F) -> R): R? = try {
        fieldClosure()
    }
    catch(e: JDODetachedFieldAccessException) {
        null
    }.nullOr(body)

    fun readSimpleEmbedded(closure: () -> TestDataSimpleEmbedded?) = readField(closure) { it.value }

    fun readComplexEmbedded(closure: () -> TestDataComplexEmbedded?) = readField(closure) { e -> TestDataEmbeddedExample(e.value, readSimpleEmbedded { e.nestedEmbedded }) }

    fun readGrandChild(closure: () -> TestDataGrandChild?) = readField(closure) { g -> TestDataGrandChildExample(g.value, readSimpleEmbedded { g.embedded1 }, readComplexEmbedded { g.embedded2 }) }

    fun readChild(closure: () -> TestDataChild?) = readField(closure) { c -> TestDataChildExample(c.value, readGrandChild { c.grandChild1 }, readGrandChild { c.grandChild2 }) }
    //endregion

    return TestDataParentExample(
            value,
            readSimpleEmbedded { embedded1 },
            readComplexEmbedded { embedded2 },
            readChild { child1 },
            readChild { child2 }
    )
}

@JsonData
internal data class TestDataParentExample(
        val v: Int,
        val e1: Int? = null,
        val e2: TestDataEmbeddedExample? = null,
        val c1: TestDataChildExample?,
        val c2: TestDataChildExample?
)

internal data class TestDataChildExample(
        val v: Int,
        val g1: TestDataGrandChildExample?,
        val g2: TestDataGrandChildExample?
)

internal data class TestDataGrandChildExample(
        val v: Int,
        val e1: Int? = null,
        val e2: TestDataEmbeddedExample? = null
)

internal data class TestDataEmbeddedExample(
        val v: Int,
        val n: Int?
)
//endregion