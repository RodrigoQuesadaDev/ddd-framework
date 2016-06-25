@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.PassedEntityInstancesNotModified.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data.CommandTestDataServices
import com.rodrigodev.common.spec.story.steps.SpecSteps
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.jbehave.core.steps.ParameterConverters
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.jdo.JDOHelper
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 01/12/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class PassedEntityInstancesNotModified : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<PassedEntityInstancesNotModified>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    class LocalSteps @Inject constructor(
            private val testDataRepositoryManager: TestDataRepositoryManager,
            override val testDataServices: CommandTestDataServices,
            private val testDataObserver: EntityObserver<TestData>,
            private val testDataQueries: TestDataQueries
    ) : SpecSteps(), UsageTypeSteps {

        private lateinit var keptEntity: TestData
        private var keptEntityVersion: Long by notNull()

        override val converters: Array<ParameterConverters.ParameterConverter> = arrayOf(SimpleUsageTypeConverter(), CollectionUsageTypeConverter())

        @Given("entities [\$values]")
        fun givenEntities(values: MutableList<Int>) {
            testDataRepositoryManager.clear()
            values.forEach { testDataServices.execute(TestDataServices.AddData(it)) }
        }

        @When("I get entity with value \$value and keep it for later use")
        fun whenIGetEntityWithValueAndKeepItForLaterUse(value: Int) {
            keptEntity = testDataObserver.observe(testDataQueries.valueIs(value)).testSubscribe().firstEvent()!!
            keptEntityVersion = keptEntity.version
        }

        @When("I pass the kept instance to an application service that modifies it, using \$usageType")
        fun whenIPassTheKeptInstanceToAnApplicationServiceThatModifiesItUsingASimpleField(usageType: SimpleUsageType) {
            keptEntity.modify(usageType)
        }

        @When("I pass the kept instance to an application service that modifies it along entities [\$existingValues], using \$usageType")
        fun whenIPassTheKeptInstanceToAnApplicationServiceThatModifiesItAlongEntitiesUsing(existingValues: MutableList<Int>, usageType: CollectionUsageType) {
            listWithKeptEntity(existingValues).modify(usageType)
        }

        @Then("the instance was never modified by the called service")
        fun thenTheInstanceWasNeverModifiedByTheCalledService() {
            assertThat(keptEntity.version).isEqualTo(keptEntityVersion)
            assertThat(JDOHelper.isDirty(keptEntity)).isFalse()
        }

        private inline fun listWithKeptEntity(existingValues: MutableList<Int>) = existingValues.queryEntities() + keptEntity

        private inline fun Iterable<Int>.queryEntities() = map {
            testDataObserver.observe(testDataQueries.valueIs(it)).testSubscribe().firstEvent()!!
        }
    }
}