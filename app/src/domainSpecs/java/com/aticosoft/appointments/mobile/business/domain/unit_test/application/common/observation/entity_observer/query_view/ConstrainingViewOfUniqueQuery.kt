package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.ConstrainingViewOfUniqueQuery.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParent
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
internal class ConstrainingViewOfUniqueQuery : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<ConstrainingViewOfUniqueQuery>

    class TestApplicationImpl : TestApplication(DaggerConstrainingViewOfUniqueQuery_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val services: AbstractConstrainingViewSteps.Services
    ) : ConstrainingViewOfSingleEntitySteps(services) {

        override fun useAParentOnlyFilter(value: Int) {
            filter = EntityObservationFilter(TestDataParent::class.java) { it.value == value }
        }

        override fun observeTheParentWithValue(value: Int): TestSubscriber<TestDataParent?> {
            return s.testDataParentObserver.observe(s.testDataParentQueries.valueIs(value), queryView).testSubscribe()
        }
    }
}
