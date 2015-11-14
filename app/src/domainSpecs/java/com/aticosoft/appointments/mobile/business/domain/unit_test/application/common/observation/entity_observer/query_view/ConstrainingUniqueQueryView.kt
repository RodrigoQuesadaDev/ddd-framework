package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.ConstrainingUniqueQueryView.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParentRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParentServices
import com.rodrigodev.common.spec.story.SpecSteps
import dagger.Component
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Pending
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class ConstrainingUniqueQueryView : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<ConstrainingUniqueQueryView>

    class TestApplicationImpl : TestApplication(DaggerConstrainingUniqueQueryView_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val testDataParentRepositoryManager: TestDataParentRepositoryManager,
            private val testDataParentServices: TestDataParentServices
    ) : SpecSteps() {

        @Given("data:\$data")
        @Pending
        fun givenData() {
            testDataParentRepositoryManager.clear()
        }

        @When("I use the query view \$queryView")
        @Pending
        fun whenIUseTheQueryView(queryView: String) {
            // PENDING
        }

        @When("I'm observing the parent with value \$root")
        @Pending
        fun whenImObservingTheParentWithValue(root: String) {
            // PENDING
        }

        @Then("the observed value is \$result")
        @Pending
        fun thenTheObservedValueIs(result: String) {
            // PENDING
        }
    }
}