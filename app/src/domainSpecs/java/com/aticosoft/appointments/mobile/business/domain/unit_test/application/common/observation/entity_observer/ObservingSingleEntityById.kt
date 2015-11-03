package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestByIdFilter
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataParentObserver
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataParentQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.ObservingSingleEntityById.TestApplicationImpl
import com.rodrigodev.common.spec.story.SpecSteps
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.jbehave.core.annotations.Given
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 06/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class ObservingSingleEntityById : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps, localSteps.entityObserverSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<ObservingSingleEntityById>

    class TestApplicationImpl : TestApplication(DaggerObservingSingleEntityById_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            val entityObserverSteps: EntityObserverUniqueEntitySteps,
            private val testDataParentQueries: TestDataParentQueries,
            private val testDataParentObserver: TestDataParentObserver
    ) : SpecSteps() {

        @Given("observation filter \$filter")
        fun GivenObservationFilter(filter: TestByIdFilter) {
            testDataParentObserver.byIdFilter = filter
        }

        @Given("I'm observing parent entity with value \$value")
        fun givenImObservingParentEntityWithValue(value: Int) {
            with(entityObserverSteps) {
                val data = testDataParentObserver.observe(testDataParentQueries.valueIs(value)).testSubscribe().firstEvent()!!
                testSubscriber = testDataParentObserver.observe(data.id).testSubscribe()
            }
        }
    }
}