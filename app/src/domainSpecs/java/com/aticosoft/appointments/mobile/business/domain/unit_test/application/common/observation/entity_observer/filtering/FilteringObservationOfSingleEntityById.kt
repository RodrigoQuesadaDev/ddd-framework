package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfSingleEntityById.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestByIdFilter
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentObserver
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentQueries
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.jbehave.core.annotations.Given
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 06/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class FilteringObservationOfSingleEntityById : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<FilteringObservationOfSingleEntityById>

    class TestApplicationImpl : TestApplication(DaggerFilteringObservationOfSingleEntityById_TestApplicationComponentImpl::class.java)

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