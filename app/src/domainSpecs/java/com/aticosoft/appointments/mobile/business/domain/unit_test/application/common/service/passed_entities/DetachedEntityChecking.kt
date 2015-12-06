@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities

import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.NonDetachedEntityException
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.DetachedEntityChecking.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data.LocalTestDataServices
import com.rodrigodev.common.spec.story.SpecSteps
import com.rodrigodev.common.test.catchThrowable
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.BeforeScenario
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.jbehave.core.steps.ParameterConverters
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 01/12/15.
 */
@Config(application = TestApplicationImpl::class)
internal class DetachedEntityChecking : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<DetachedEntityChecking>

    class TestApplicationImpl : TestApplication(DaggerDetachedEntityChecking_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val testDataRepositoryManager: TestDataRepositoryManager,
            override val testDataServices: LocalTestDataServices,
            private val testDataObserver: TestDataObserver,
            private val testDataQueries: TestDataQueries,
            private val entityContext: Entity.Context
    ) : SpecSteps(), UsageTypeSteps {
        private var throwable: Throwable? = null

        override val converters: Array<ParameterConverters.ParameterConverter> = arrayOf(SimpleUsageTypeConverter(), CollectionUsageTypeConverter())

        @BeforeScenario
        fun beforeScenario() {
            throwable = null
        }

        @Given("entities [\$values]")
        fun givenEntities(values: MutableList<Int>) {
            testDataRepositoryManager.clear()
            values.forEach { testDataServices.execute(TestDataServices.AddData(it)) }
        }

        @When("I create a new entity and pass it to an application service, using \$usageType")
        fun whenICreateANewEntityAndPassItToAnApplicationServiceUsing(usageType: SimpleUsageType) {
            throwable = catchThrowable { createNewEntity().modify(usageType) }
        }

        @When("I create a new entity and pass it to an application service along entities [\$existingValues], using \$usageType")
        fun whenICreateANewEntityAndPassItToAnApplicationServiceAlongEntities(existingValues: MutableList<Int>, usageType: CollectionUsageType) {
            throwable = catchThrowable { listWithNewEntity(existingValues).modify(usageType) }
        }

        @Then("the system throws an exception indicating it's not detached")
        fun thenTheSystemThrowsAnExceptionIndicatingItsNotDetached() {
            assertThat(throwable).isInstanceOf(NonDetachedEntityException::class.java)
        }

        private inline fun listWithNewEntity(existingValues: MutableList<Int>) = existingValues.queryEntities() + createNewEntity()

        private inline fun createNewEntity() = TestData(entityContext, 123)

        private inline fun Iterable<Int>.queryEntities() = map {
            testDataObserver.observe(testDataQueries.valueIs(it)).testSubscribe().firstEvent()!!
        }
    }
}