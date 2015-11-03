package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataParentObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestIsPrimeFilter
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataParentQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.ObservingCountQuery.TestApplicationImpl
import com.rodrigodev.common.spec.story.SpecSteps
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.jbehave.core.annotations.Given
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 13/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class ObservingCountQuery : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps, localSteps.entityObserverSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<ObservingCountQuery>

    class TestApplicationImpl : TestApplication(DaggerObservingCountQuery_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            val entityObserverSteps: EntityObserverEntityCountSteps,
            private val testDataParentQueries: TestDataParentQueries,
            private val testDataParentObserver: TestDataParentObserver
    ) : SpecSteps() {

        private var isPrimeFilter: TestIsPrimeFilter by notNull()

        @Given("observation filter \$filter")
        fun GivenObservationFilter(filter: TestIsPrimeFilter) {
            isPrimeFilter = filter
        }

        @Given("I'm observing the amount of parent entities with prime value")
        fun givenImObservingTheAmountOfParentEntitiesWithPrimeValue() {
            with(entityObserverSteps) {
                testSubscriber = testDataParentObserver.observe(testDataParentQueries.isPrimeCount(isPrimeFilter)).testSubscribe()
            }
        }
    }
}