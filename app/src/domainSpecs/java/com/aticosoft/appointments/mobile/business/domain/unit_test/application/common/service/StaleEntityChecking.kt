@file:Suppress("NOTHING_TO_INLINe")

package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.NonExistingStalePersistableObjectException
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.StalePersistableObjectException
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.*
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEntityRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.StaleEntityChecking.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data.CommandTestDataServices
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.spec.story.steps.SpecSteps
import com.rodrigodev.common.test.catchThrowable
import com.rodrigodev.common.rx.testing.firstEvent
import com.rodrigodev.common.rx.testing.testSubscribe
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.jbehave.core.steps.ParameterConverters
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class StaleEntityChecking : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<StaleEntityChecking>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val testDataRepositoryManager: TestEntityRepositoryManager<TestData>,
            override val testDataServices: CommandTestDataServices,
            private val testDataObserver: EntityObserver<TestData>,
            private val testDataQueries: TestDataQueries
    ) : SpecSteps(), UsageTypeSteps, ExceptionThrowingSteps {

        private lateinit var keptEntity: TestData
        override var throwable: Throwable? = null

        override val converters: Array<ParameterConverters.ParameterConverter> = arrayOf(SimpleUsageTypeConverter(), CollectionUsageTypeConverter())

        @Given("entities [\$values]")
        fun givenEntities(values: MutableList<Int>) {
            testDataRepositoryManager.clear()
            values.forEach { testDataServices.execute(AddData(it)) }
        }

        @When("I get entity with value \$value and keep it for later use")
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

        @When("I pass that entity to an application service that uses it without modifying it, using \$usageType")
        fun whenIPassThatEntityToAnApplicationServiceThatUsesItWithoutModifyingItUsingASimpleField(usageType: SimpleUsageType) {
            throwable = catchThrowable { keptEntity.onlyUse(usageType) }
        }

        @When("I pass that entity to an application service that uses it without modifying it, along entities [\$existingValues] and using \$usageType")
        fun whenIPassThatEntityToAnApplicationServiceThatUsesItWithoutModifyingItAlongEntities(existingValues: MutableList<Int>, usageType: CollectionUsageType) {
            throwable = catchThrowable { listWithKeptEntity(existingValues).onlyUseEntities(usageType) }
        }

        @Then("the system throws an exception indicating it's stale")
        fun thenTheSystemThrowsAnExceptionIndicatingItsStale() {
            assertThat(throwable).isInstanceOf(StalePersistableObjectException::class.java)
        }

        @Then("the system throws an exception indicating that it doesn't exist anymore")
        fun thenTheSystemThrowsAnExceptionIndicatingThatItDoesNotExistAnymore() {
            assertThat(throwable).isInstanceOf(NonExistingStalePersistableObjectException::class.java)
        }

        private inline fun listWithKeptEntity(existingValues: MutableList<Int>) = existingValues.queryEntities() + keptEntity

        private inline fun Iterable<Int>.queryEntities() = map {
            testDataObserver.observe(testDataQueries.valueIs(it)).testSubscribe().firstEvent()!!
        }
    }
}