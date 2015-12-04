@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities

import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.DirtyEntityException
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.AddData
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.DirtyEntityChecking.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.UsageType.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data.LocalTestDataServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data.LocalTestDataServices.*
import com.rodrigodev.common.spec.story.SpecSteps
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.jbehave.core.annotations.BeforeScenario
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.jbehave.core.steps.ParameterConverters
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
@Config(application = TestApplicationImpl::class)
internal class DirtyEntityChecking : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<DirtyEntityChecking>

    class TestApplicationImpl : TestApplication(DaggerDirtyEntityChecking_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val testDataRepositoryManager: TestDataRepositoryManager,
            private val testDataServices: LocalTestDataServices,
            private val testDataObserver: TestDataObserver,
            private val testDataQueries: TestDataQueries
    ) : SpecSteps() {
        private lateinit var keptEntity: TestData
        private var throwable: Throwable? = null

        override val converters: Array<ParameterConverters.ParameterConverter> = arrayOf(UsageTypeConverter())

        @BeforeScenario
        fun beforeScenario() {
            throwable = null
        }

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

        @When("I call an application service passing that entity to it, using a simple field")
        fun whenICallAnApplicationServicePassingThatEntityToIt() {
            throwable = catchThrowable { testDataServices.execute(ModifyEntity(keptEntity)) }
        }

        @When("I call an application service passing it that entity along entities [\$existingValues], using \$usageType")
        fun whenICallAnApplicationServicePassingItThatEntityAlongEntitiesUsing(existingValues: MutableList<Int>, usageType: UsageType) {
            val passedEntities = listWithKeptEntity(existingValues)
            throwable = catchThrowable {
                when (usageType) {
                    LIST -> testDataServices.execute(ModifyEntitiesFromList(passedEntities))
                    SET -> testDataServices.execute(ModifyEntitiesFromSet(passedEntities.toSet()))
                    VALUES_OF_MAP -> testDataServices.execute(ModifyEntitiesFromMapAsValues(passedEntities.toMapBy { it.value }))
                    KEYS_OF_MAP -> testDataServices.execute(ModifyEntitiesFromMapAsKeys(passedEntities.toMap({ it }, { it.value })))
                }
            }
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