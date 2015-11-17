package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfUniqueQuery.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentObserver
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestValueIsFilter
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.jbehave.core.annotations.Given
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 11/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class FilteringObservationOfUniqueQuery : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<FilteringObservationOfUniqueQuery>

    class TestApplicationImpl : TestApplication(DaggerFilteringObservationOfUniqueQuery_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val services: AbstractFilteringObservationSteps.Services,
            private val testDataParentQueries: TestDataParentQueries,
            private val testDataParentObserver: TestDataParentObserver
    ) : FilteringObservationUniqueEntitySteps(services) {

        private var valueIsFilter: TestValueIsFilter by notNull()

        @Given("observation filter \$filter")
        fun GivenObservationFilter(filter: TestValueIsFilter) {
            valueIsFilter = filter
        }

        override fun observeTheParentEntityWithValue(value: Int): TestSubscriber<TestDataParent?> {
            return testDataParentObserver.observe(testDataParentQueries.valueIs(value, valueIsFilter)).testSubscribe()
        }
    }
}