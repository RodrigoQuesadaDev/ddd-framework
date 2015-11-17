package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.ConstrainingViewOfListQuery.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParentObserver
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParentQueries
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.number.isOdd
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.JDODetachedFieldAccessException
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 20/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class ConstrainingViewOfListQuery : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<ConstrainingViewOfListQuery>

    class TestApplicationImpl : TestApplication(DaggerConstrainingViewOfListQuery_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val services: AbstractConstrainingViewSteps.Services,
            private val testDataParentObserver: TestDataParentObserver,
            private val testDataParentQueries: TestDataParentQueries
    ) : AbstractConstrainingViewSteps(services) {

        private var testSubscriber by notNull<TestSubscriber<List<TestDataParent>>>()

        @Given("an odd-parent filter that only filters parents is being used for the observation")
        fun givenAnOddParentFilterIsBeingUsedForTheObservation() {
            filter = EntityObservationFilter(TestDataParent::class.java) { it.value.isOdd() }
        }

        @When("I'm observing the parents with odd value")
        fun whenImObservingTheParentsWithOddValue() {
            testSubscriber = testDataParentObserver.observe(
                    testDataParentQueries.isOdd(*filter?.let { arrayOf(it) } ?: emptyArray()),
                    queryView
            ).testSubscribe()
        }

        @Then("the observed value is \$result")
        fun thenTheObservedValueIs(result: MutableList<TestDataParentExample>) {
            assertThat(testSubscriber.firstEvent().map (TestDataParent::toExample)).isEqualTo(result)
        }

        @Then("later the observed values were \$result")
        fun thenLaterTheObservedValuesWere(result: MutableList<MutableList<TestDataParentExample>>) {
            advanceTime()
            assertThat(testSubscriber.onNextEvents.map { list -> list.map(TestDataParent::toExample) })
                    .containsExactlyElementsOf(result)
        }
    }
}

//TODO remove when Kotlin > 1.0.0-beta-2423
private fun TestDataParent.toExample() = TestDataParentExample(
        value,
        readField { child1 }?.let { c -> TestDataChildExample(c.value, readField { c.grandChild1 }?.value, readField { c.grandChild2 }?.value) },
        readField { child2 }?.let { c -> TestDataChildExample(c.value, readField { c.grandChild1 }?.value, readField { c.grandChild2 }?.value) }
)

//TODO remove when Kotlin > 1.0.0-beta-2423
private inline fun <T : Any> readField(body: () -> T?): T? = try {
    body()
}
catch(e: JDODetachedFieldAccessException) {
    null
}