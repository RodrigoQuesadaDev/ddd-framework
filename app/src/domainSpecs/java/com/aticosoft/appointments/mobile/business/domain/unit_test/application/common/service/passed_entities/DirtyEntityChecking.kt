@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.DirtyEntityException
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.AddData
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.DirtyEntityChecking.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data.CommandTestDataServices
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.spec.story.steps.SpecSteps
import com.rodrigodev.common.test.catchThrowable
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
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
internal class DirtyEntityChecking : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<DirtyEntityChecking>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val testDataRepositoryManager: TestDataRepositoryManager,
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

        @When("I get entity with value \$value, modify it and then keep it for later use")
        fun whenIGetEntityWithValueModifyItAndThenKeepItForLaterUse(value: Int) {
            keptEntity = testDataObserver.observe(testDataQueries.valueIs(value)).testSubscribe().firstEvent()!!.apply {
                ++this.value
            }
        }

        @When("I call an application service passing that entity to it, using \$usageType")
        fun whenICallAnApplicationServicePassingThatEntityToItUsing(usageType: SimpleUsageType) {
            throwable = catchThrowable { keptEntity.modify(usageType) }
        }

        @When("I call an application service passing it that entity along entities [\$existingValues], using \$usageType")
        fun whenICallAnApplicationServicePassingItThatEntityAlongEntitiesUsing(existingValues: MutableList<Int>, usageType: CollectionUsageType) {
            throwable = catchThrowable { listWithKeptEntity(existingValues).modify(usageType) }
        }

        @Then("the system throws an exception indicating it's dirty")
        fun thenTheSystemThrowsAnExceptionIndicatingItSDirty() {
            assertThat(throwable).isInstanceOf(DirtyEntityException::class.java)
        }

        private inline fun listWithKeptEntity(existingValues: MutableList<Int>) = existingValues.queryEntities() + keptEntity

        private inline fun Iterable<Int>.queryEntities() = map {
            testDataObserver.observe(testDataQueries.valueIs(it)).testSubscribe().firstEvent()!!
        }
    }
}