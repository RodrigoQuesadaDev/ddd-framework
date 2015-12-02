package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.DirtyEntityException
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.AddData
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.ModifyEntity
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.DirtyEntityIsPassed.TestApplicationImpl
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class DirtyEntityIsPassed : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<DirtyEntityIsPassed>

    class TestApplicationImpl : TestApplication(DaggerDirtyEntityIsPassed_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val testDataRepositoryManager: TestDataRepositoryManager,
            private val testDataServices: TestDataServices,
            private val testDataObserver: TestDataObserver,
            private val testDataQueries: TestDataQueries
    ) {
        private lateinit var retrievedEntity: TestData
        private var throwable: Throwable? = null

        @Given("entities [\$values]")
        fun givenEntities(values: MutableList<Int>) {
            testDataRepositoryManager.clear()
            values.forEach { testDataServices.execute(AddData(it)) }
        }

        @When("I get entity with value \$value")
        fun whenIGetEntityWithValue(value: Int) {
            retrievedEntity = testDataObserver.observe(testDataQueries.valueIs(value)).testSubscribe().firstEvent()!!
        }

        @When("I modify its value outside the domain layer")
        fun whenIModifyItsValueOutsideTheDomainLayer() {
            ++retrievedEntity.value
        }

        @When("I call an application service passing that entity to it")
        fun whenICallAnApplicationServicePassingThatEntityToIt() {
            throwable = catchThrowable { testDataServices.execute(ModifyEntity(retrievedEntity)) }
        }

        @Then("the system throws an exception indicating it's dirty")
        fun thenTheSystemThrowsAnExceptionIndicatingItSDirty() {
            assertThat(throwable).isInstanceOf(DirtyEntityException::class.java)
        }
    }
}