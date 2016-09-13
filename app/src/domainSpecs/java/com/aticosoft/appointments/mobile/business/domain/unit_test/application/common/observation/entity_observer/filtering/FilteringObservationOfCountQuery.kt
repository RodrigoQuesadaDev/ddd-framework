package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfCountQuery.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestIsPrimeFilter
import com.rodrigodev.common.rx.testing.testSubscribe
import org.assertj.core.api.Assertions
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 13/11/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class FilteringObservationOfCountQuery : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<FilteringObservationOfCountQuery>(UnitTestApplicationComponent::inject)

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
        private lateinit var testSubscriber: TestSubscriber<Long>

        @Given("observation filter \$filter")
        fun GivenObservationFilter(filter: TestIsPrimeFilter) {
            isPrimeFilter = filter
        }

        @Given("I'm observing the amount of parent entities with prime value")
        fun givenImObservingTheAmountOfParentEntitiesWithPrimeValue() {
            testSubscriber = testDataParentObserver.observe(testDataParentQueries.isPrimeCount(isPrimeFilter)).testSubscribe()
        }

        @Then("later the values observed were \$result")
        fun thenLaterTheValuesObservedWere(result: MutableList<Long>) {
            advanceTime()
            Assertions.assertThat(testSubscriber.onNextEvents).containsExactlyElementsOf(result)
        }
    }
}