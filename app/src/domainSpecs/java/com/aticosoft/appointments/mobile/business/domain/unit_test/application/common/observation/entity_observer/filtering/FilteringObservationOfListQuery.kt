package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfListQuery.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParent
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
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 11/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class FilteringObservationOfListQuery : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<FilteringObservationOfListQuery>

    class TestApplicationImpl : TestApplication(DaggerFilteringObservationOfListQuery_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val services: AbstractFilteringObservationSteps.Services,
            private val testDataParentQueries: TestDataParentQueries,
            private val testDataParentObserver: TestDataParentObserver
    ) : AbstractFilteringObservationSteps(services) {

        private var isPrimeFilter: TestIsPrimeFilter by notNull()
        private var testSubscriber: TestSubscriber<List<TestDataParent>> by notNull()

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

//TODO remove when Kotlin > 1.0.0-beta-2423
private fun TestDataParent.toExample() = TestDataParentExample(value, child.value)