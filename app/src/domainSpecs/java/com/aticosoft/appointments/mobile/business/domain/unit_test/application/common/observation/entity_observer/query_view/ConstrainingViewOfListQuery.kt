package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.ConstrainingViewOfListQuery.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParentQueries
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.number.isOdd
import com.rodrigodev.common.testing.testSubscribe
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 20/11/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class ConstrainingViewOfListQuery : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<ConstrainingViewOfListQuery>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    class LocalSteps @Inject constructor(
            private val services: AbstractConstrainingViewSteps.Services,
            private val testDataParentObserver: EntityObserver<TestDataParent>,
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