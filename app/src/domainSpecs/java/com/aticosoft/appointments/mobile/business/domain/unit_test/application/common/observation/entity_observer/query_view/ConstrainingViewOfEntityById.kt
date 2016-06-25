package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.ConstrainingViewOfEntityById.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParent
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class ConstrainingViewOfEntityById : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<ConstrainingViewOfEntityById>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

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