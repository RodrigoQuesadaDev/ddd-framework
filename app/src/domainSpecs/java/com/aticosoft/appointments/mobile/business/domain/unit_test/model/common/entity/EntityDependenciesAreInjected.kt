package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.AddData
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.UseDependency
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataFactory
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.EntityDependenciesAreInjected.TestApplicationImpl
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.test.catchThrowable
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.BeforeScenario
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 11/12/15.
 */
@Config(application = TestApplicationImpl::class)
internal class EntityDependenciesAreInjected : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<EntityDependenciesAreInjected>

    class TestApplicationImpl : TestApplication(DaggerEntityDependenciesAreInjected_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val testDataFactory: TestDataFactory,
            private val testDataRepositoryManager: TestDataRepositoryManager,
            private val testDataServices: TestDataServices,
            private val testDataObserver: TestDataObserver,
            private val testDataQueries: TestDataQueries
    ) : ExceptionThrowingSteps {

        private var entity: TestData? = null
        override var throwable: Throwable? = null

        @BeforeScenario
        fun resetEntity() {
            entity = null
        }

        @Given("a new entity")
        fun givenANewEntity() {
            entity = testDataFactory.create(123)
        }

        @Given("entities [\$values]")
        fun givenEntities(values: MutableList<Int>) {
            values.forEach { testDataServices.execute(AddData(it)) }
        }

        @When("I clear the datastore cache")
        fun whenIClearTheDatastoreCache() {
            testDataRepositoryManager.clearCache()
        }

        @When("I retrieve the entity with value \$value")
        fun whenIRetrieveTheEntityWithValue(value: Int) {
            entity = testDataObserver.observe(testDataQueries.valueIs(value)).testSubscribe().firstEvent()
        }

        @When("I call an application service that makes use of an injected dependency for entity with value \$value")
        fun whenICallAnApplicationServiceThatMakesUseOfAnInjectedDependency(value: Int) {
            throwable = catchThrowable { testDataServices.execute(UseDependency(value)) }
        }

        @Then("the method call is successful")
        fun thenTheMethodCallIsSuccessful() {
            assertThat(throwable).isNull()
        }

        @Then("the dependency was set")
        fun thenTheDependencyWasSet() {
            assertThat(entity!!.c).isNotNull()
        }

        @Then("the dependency was not set")
        fun thenTheDependencyWasNotSet() {
            assertThat(entity!!.c).isNull()
        }
    }
}