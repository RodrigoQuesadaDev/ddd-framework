package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfListQuery.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestIsPrimeFilter
import com.rodrigodev.common.testing.testSubscribe
import org.assertj.core.api.Assertions
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 11/11/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class FilteringObservationOfListQuery : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<FilteringObservationOfListQuery>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val services: AbstractFilteringObservationSteps.Services,
            private val testDataParentQueries: TestDataParentQueries,
            private val testDataParentObserver: EntityObserver<TestDataParent>
    ) : AbstractFilteringObservationSteps(services) {

        private lateinit var isPrimeFilter: TestIsPrimeFilter
        private lateinit var testSubscriber: TestSubscriber<List<TestDataParent>>

        @Given("observation filter \$filter")
        fun GivenObservationFilter(filter: TestIsPrimeFilter) {
            isPrimeFilter = filter
        }

        @Given("I'm observing parent entities with prime value")
        fun givenImObservingParentEntitiesWithPrimeValue() {
            testSubscriber = testDataParentObserver.observe(testDataParentQueries.isPrime(isPrimeFilter)).testSubscribe()
        }

        @Then("later the values observed were \$result")
        fun thenLaterTheValuesObservedWere(result: MutableList<MutableList<TestDataParentExample>>) {
            advanceTime()
            Assertions.assertThat(testSubscriber.onNextEvents.map { list -> list.map(TestDataParent::toExample) })
                    .containsExactlyElementsOf(result)
        }
    }
}