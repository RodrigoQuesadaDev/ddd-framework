package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataParentObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestValueIsFilter
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataParentQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.ObservingUniqueQuery.TestApplicationImpl
import com.rodrigodev.common.spec.story.SpecSteps
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.jbehave.core.annotations.Given
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 11/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class ObservingUniqueQuery : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps, localSteps.entityObserverSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<ObservingUniqueQuery>

    class TestApplicationImpl : TestApplication(DaggerObservingUniqueQuery_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            val entityObserverSteps: EntityObserverUniqueEntitySteps,
            private val testDataParentQueries: TestDataParentQueries,
            private val testDataParentObserver: TestDataParentObserver
    ) : SpecSteps() {

        private var valueIsFilter: TestValueIsFilter by notNull()

        @Given("observation filter \$filter")
        fun GivenObservationFilter(filter: TestValueIsFilter) {
            valueIsFilter = filter
        }

        @Given("I'm observing parent entity with value \$value")
        fun givenImObservingParentEntityWithValue(value: Int) {
            with(entityObserverSteps) {
                testSubscriber = testDataParentObserver.observe(testDataParentQueries.valueIs(value, valueIsFilter)).testSubscribe()
            }
        }
    }
}