package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.ConstrainingViewOfEntityById.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParent
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class ConstrainingViewOfEntityById : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<ConstrainingViewOfEntityById>

    class TestApplicationImpl : TestApplication(DaggerConstrainingViewOfEntityById_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val services: AbstractConstrainingViewSteps.Services
    ) : ConstrainingViewOfSingleEntitySteps(services) {

        override fun useAParentOnlyFilter(value: Int) {
            val parent = observedParent(value)
            filter = EntityObservationFilter(TestDataParent::class.java) { it.id == parent.id }
        }

        override fun observeTheParentWithValue(value: Int): TestSubscriber<TestDataParent?> {
            val parent = observedParent(value)
            return s.testDataParentObserver.observe(parent.id, queryView).testSubscribe()
        }

        private fun observedParent(value: Int) = s.testDataParentObserver.observe(s.testDataParentQueries.valueIs(value)).testSubscribe().firstEvent()!!
    }
}
