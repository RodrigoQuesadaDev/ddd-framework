package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfCountQuery.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentObserver
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestIsPrimeFilter
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.assertj.core.api.Assertions
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 13/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class FilteringObservationOfCountQuery : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<FilteringObservationOfCountQuery>

    class TestApplicationImpl : TestApplication(DaggerFilteringObservationOfCountQuery_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val services: AbstractFilteringObservationSteps.Services,
            private val testDataParentQueries: TestDataParentQueries,
            private val testDataParentObserver: TestDataParentObserver
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