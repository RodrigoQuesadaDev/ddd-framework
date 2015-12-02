package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.ModifyEntity
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.PassedEntityInstanceIsNotModified.TestApplicationImpl
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.JDOHelper
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 01/12/15.
 */
@Config(application = TestApplicationImpl::class)
internal class PassedEntityInstanceIsNotModified : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<PassedEntityInstanceIsNotModified>

    class TestApplicationImpl : TestApplication(DaggerPassedEntityInstanceIsNotModified_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val testDataRepositoryManager: TestDataRepositoryManager,
            private val testDataServices: TestDataServices,
            private val testDataObserver: TestDataObserver,
            private val testDataQueries: TestDataQueries
    ) {
        private lateinit var keptEntity: TestData
        private var keptEntityVersion: Long by notNull()

        @Given("entities [\$values]")
        fun givenEntities(values: MutableList<Int>) {
            testDataRepositoryManager.clear()
            values.forEach { testDataServices.execute(TestDataServices.AddData(it)) }
        }

        @When("I get entity with value \$value and keep it somewhere for later usage")
        fun whenIGetEntityWithValueAndKeepItSomewhereForLaterUsage(value: Int) {
            keptEntity = testDataObserver.observe(testDataQueries.valueIs(value)).testSubscribe().firstEvent()!!
        }

        @When("I pass the kept instance to an application service that modifies it")
        fun whenIPassTheKeptInstanceToAnApplicationServiceThatModifiesIt() {
            keptEntityVersion = keptEntity.version
            testDataServices.execute(ModifyEntity(keptEntity))
        }

        @Then("the instance was never modified by the called service")
        fun thenTheInstanceWasNeverModifiedByTheCalledService() {
            assertThat(keptEntity.version).isEqualTo(keptEntityVersion)
            assertThat(JDOHelper.isDirty(keptEntity)).isFalse()
        }
    }
}