package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.NonExistingStaleEntityException
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.StaleEntityException
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.*
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.StaleEntityIsUsed.TestApplicationImpl
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Pending
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class StaleEntityIsUsed : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<StaleEntityIsUsed>

    class TestApplicationImpl : TestApplication(DaggerStaleEntityIsUsed_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val testDataRepositoryManager: TestDataRepositoryManager,
            private val testDataServices: TestDataServices,
            private val testDataObserver: TestDataObserver,
            private val testDataQueries: TestDataQueries
    ) {
        private lateinit var keptEntity: TestData
        private var throwable: Throwable? = null

        @Given("entities [\$values]")
        fun givenEntities(values: MutableList<Int>) {
            testDataRepositoryManager.clear()
            values.forEach { testDataServices.execute(TestDataServices.AddData(it)) }
        }

        @When("I get entity with value \$value and keep it somewhere for later usage")
        fun whenIGetEntityWithValueAndKeepItSomewhereForLaterUsage(value: Int) {
            keptEntity = testDataObserver.observe(testDataQueries.valueIs(value)).testSubscribe().firstEvent()!!
        }

        @When("I call an application service that modifies its value stored in the database")
        fun whenICallAnApplicationServiceThatModifiesItsValueStoredInTheDatabase() {
            testDataServices.execute(ChangeData(keptEntity.value, keptEntity.value + 1))
        }

        @When("I call an application service that removes it")
        fun whenICallAnApplicationServiceThatRemovesIt() {
            testDataServices.execute(RemoveData(keptEntity.value))
        }

        @When("I call an application service that only uses it but never modifies the database")
        fun whenICallAnApplicationServiceThatOnlyUsesItButNeverModifiesTheDatabase() {
            throwable = catchThrowable { testDataServices.execute(OnlyUseEntity(keptEntity)) }
        }

        @When("I pass the kept instance to an application service that modifies it")
        fun whenIPassTheKeptInstanceToAnApplicationServiceThatModifiesIt() {
            throwable = catchThrowable { testDataServices.execute(ModifyEntity(keptEntity)) }
        }

        @When("I pass the kept instance to an application service that modifies a different entity with value \$anotherValue")
        fun whenIPassTheKeptInstanceToAnApplicationServiceThatModifiesADifferentEntityWithValue(anotherValue: Int) {
            throwable = catchThrowable { testDataServices.execute(ModifyAnotherEntity(keptEntity, anotherValue)) }
        }

        @When("I pass the kept instance and value \$anotherValue to an application service that while executing another application service concurrently modifies the same entity")
        fun whenIPassTheKeptInstanceToAnApplicationServiceThatWhileExecutingAnotherApplicationServiceConcurrentlyModifiesTheSameEntity(anotherValue: Int) {
            throwable = catchThrowable { testDataServices.execute(ConcurrentlyModifyPassedEntity(keptEntity, anotherValue)) }
        }

        @Then("the system throws an exception indicating it's stale")
        fun thenTheSystemThrowsAnExceptionIndicatingItsStale() {
            assertThat(throwable).isInstanceOf(StaleEntityException::class.java)
        }

        @Then("the system throws an exception indicating that it doesn't exist anymore")
        fun thenTheSystemThrowsAnExceptionIndicatingThatItDoesNotExistAnymore() {
            assertThat(throwable).isInstanceOf(NonExistingStaleEntityException::class.java)
        }
    }
}