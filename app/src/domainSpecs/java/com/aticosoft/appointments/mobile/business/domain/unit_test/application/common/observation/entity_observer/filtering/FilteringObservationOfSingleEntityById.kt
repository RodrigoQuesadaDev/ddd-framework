package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfSingleEntityById.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestByIdFilter
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentObserver
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentQueries
import com.rodrigodev.common.rx.testing.firstEvent
import com.rodrigodev.common.rx.testing.testSubscribe
import org.jbehave.core.annotations.Given
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 06/11/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class FilteringObservationOfSingleEntityById : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<FilteringObservationOfSingleEntityById>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val services: AbstractFilteringObservationSteps.Services,
            private val testDataParentQueries: TestDataParentQueries,
            private val testDataParentObserver: TestDataParentObserver
    ) : FilteringObservationUniqueEntitySteps(services) {

        @Given("observation filter \$filter")
        fun GivenObservationFilter(filter: TestByIdFilter) {
            testDataParentObserver.byIdFilter = filter
        }

        override fun observeTheParentEntityWithValue(value: Int): TestSubscriber<TestDataParent?> {
            val data = testDataParentObserver.observe(testDataParentQueries.valueIs(value)).testSubscribe().firstEvent()!!
            return testDataParentObserver.observe(data.id).testSubscribe()
        }
    }
}