package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.NonDetachedEntityException
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.ModifyEntity
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.NonDetachedEntityIsPassed.TestApplicationImpl
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 01/12/15.
 */
@Config(application = TestApplicationImpl::class)
internal class NonDetachedEntityIsPassed : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<NonDetachedEntityIsPassed>

    class TestApplicationImpl : TestApplication(DaggerNonDetachedEntityIsPassed_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val testDataServices: TestDataServices,
            private val entityContext: Entity.Context
    ) {
        private var throwable: Throwable? = null

        @When("I create a new entity and pass it to an application service")
        fun whenICreateANewEntityAndPassItToAnApplicationService() {
            throwable = catchThrowable { testDataServices.execute(ModifyEntity(TestData(entityContext, 3))) }
        }

        @Then("the system throws an exception indicating it's not detached")
        fun thenTheSystemThrowsAnExceptionIndicatingItsNotDetached() {
            assertThat(throwable).isInstanceOf(NonDetachedEntityException::class.java)
        }
    }
}