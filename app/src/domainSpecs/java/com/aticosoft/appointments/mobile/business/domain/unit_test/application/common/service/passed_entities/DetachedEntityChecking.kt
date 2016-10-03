@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.NonDetachedPersistableObjectException
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEntityRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataFactory
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.DetachedEntityChecking.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data.CommandTestDataServices
import com.rodrigodev.common.rx.testing.firstEvent
import com.rodrigodev.common.rx.testing.testSubscribe
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.spec.story.steps.SpecSteps
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.jbehave.core.steps.ParameterConverters
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 01/12/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class DetachedEntityChecking : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<DetachedEntityChecking>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val testDataRepositoryManager: TestEntityRepositoryManager<TestData>,
            private val testDataFactory: TestDataFactory,
            override val testDataServices: CommandTestDataServices,
            private val testDataObserver: EntityObserver<TestData>,
            private val testDataQueries: TestDataQueries
    ) : SpecSteps(), UsageTypeSteps, ExceptionThrowingSteps {

        override val converters: Array<ParameterConverters.ParameterConverter> = arrayOf(SimpleUsageTypeConverter(), CollectionUsageTypeConverter())

        override var _thrownException: Throwable? = null
        override var _catchException: Boolean = false

        @Given("entities [\$values]")
        fun givenEntities(values: MutableList<Int>) {
            testDataRepositoryManager.clear()
            values.forEach { testDataServices.execute(TestDataServices.AddData(it)) }
        }

        @When("I create a new entity and pass it to an application service, using \$usageType")
        fun whenICreateANewEntityAndPassItToAnApplicationServiceUsing(usageType: SimpleUsageType) {
            mightThrowException { createNewEntity().modify(usageType) }
        }

        @When("I create a new entity and pass it to an application service along entities [\$existingValues], using \$usageType")
        fun whenICreateANewEntityAndPassItToAnApplicationServiceAlongEntities(existingValues: MutableList<Int>, usageType: CollectionUsageType) {
            mightThrowException { listWithNewEntity(existingValues).modify(usageType) }
        }

        @Then("the system throws an exception indicating it's not detached")
        fun thenTheSystemThrowsAnExceptionIndicatingItsNotDetached() {
            assertThat(thrownException).isInstanceOf(NonDetachedPersistableObjectException::class.java)
        }

        private inline fun listWithNewEntity(existingValues: MutableList<Int>) = existingValues.queryEntities() + createNewEntity()

        private inline fun createNewEntity() = testDataFactory.create(123)

        private inline fun Iterable<Int>.queryEntities() = map {
            testDataObserver.observe(testDataQueries.valueIs(it)).testSubscribe().firstEvent()!!
        }
    }
}