package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfUniqueQuery.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestValueIsFilter
import com.rodrigodev.common.testing.testSubscribe
import org.jbehave.core.annotations.Given
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 11/11/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class FilteringObservationOfUniqueQuery : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<FilteringObservationOfUniqueQuery>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    class LocalSteps @Inject constructor(
            private val services: AbstractFilteringObservationSteps.Services,
            private val testDataParentQueries: TestDataParentQueries,
            private val testDataParentObserver: EntityObserver<TestDataParent>
    ) : FilteringObservationUniqueEntitySteps(services) {

        private lateinit var valueIsFilter: TestValueIsFilter

        @Given("observation filter \$filter")
        fun GivenObservationFilter(filter: TestValueIsFilter) {
            valueIsFilter = filter
        }

        override fun observeTheParentEntityWithValue(value: Int): TestSubscriber<TestDataParent?> {
            return testDataParentObserver.observe(testDataParentQueries.valueIs(value, valueIsFilter)).testSubscribe()
        }
    }
}