package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.ConstrainingViewOfUniqueQuery.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParent
import com.rodrigodev.common.testing.testSubscribe
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class ConstrainingViewOfUniqueQuery : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<ConstrainingViewOfUniqueQuery>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

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