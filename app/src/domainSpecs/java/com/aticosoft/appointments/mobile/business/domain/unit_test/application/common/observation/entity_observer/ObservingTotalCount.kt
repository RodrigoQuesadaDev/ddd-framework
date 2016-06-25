package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver.Companion.DATA_REFRESH_RATE_TIME
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.*
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.ObservingTotalCount.UnitTestApplicationImpl
import com.rodrigodev.common.rx.advanceTimeBy
import com.rodrigodev.common.testing.testSubscribe
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 05/11/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class ObservingTotalCount : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<ObservingTotalCount>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    class LocalSteps @Inject constructor(
            private val testDataRepositoryManager: TestDataRepositoryManager,
            private val testDataServices: TestDataServices,
            private val testDataObserver: EntityObserver<TestData>,
            private val testScheduler: TestScheduler
    ) {
        private lateinit var totalCountSubscriber: TestSubscriber<Long>

        @Given("no data")
        fun givenNoData() {
            testDataRepositoryManager.clear()
        }

        @Given("I'm observing the total count of entities")
        fun givenImObservingTheTotalCountOfEntities() {
            totalCountSubscriber = testDataObserver.observeTotalCount().testSubscribe()
        }

        @When("later I insert [\$values]")
        fun whenLaterIInsert(values: MutableList<Int>) {
            advanceTime()
            values.forEach { testDataServices.execute(AddData(it)) }
        }

        @When("later I change \$currentValue into \$targetValue")
        fun whenLaterIChangeAValueInto(currentValue: Int, targetValue: Int) {
            advanceTime()
            testDataServices.execute(ChangeData(currentValue, targetValue))
        }

        @When("later I delete [\$values]")
        fun whenLaterIDelete(values: MutableList<Int>) {
            advanceTime()
            values.forEach { testDataServices.execute(RemoveData(it)) }
        }

        @Then("later the total count values observed were [\$values]")
        fun thenLaterTheTotalCountValuesObservedWere(values: MutableList<Long>) {
            advanceTime()
            assertThat(totalCountSubscriber.onNextEvents).containsExactlyElementsOf(values)
        }

        private fun advanceTime() {
            testScheduler.advanceTimeBy(DATA_REFRESH_RATE_TIME)
        }
    }
}