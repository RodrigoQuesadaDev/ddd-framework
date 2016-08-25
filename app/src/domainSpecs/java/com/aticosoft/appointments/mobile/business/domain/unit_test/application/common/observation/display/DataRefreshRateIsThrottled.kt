package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.display

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.AddData
import com.aticosoft.appointments.mobile.business.domain.testing.common.time.TestTimeServiceImpl
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.display.DataRefreshRateIsThrottled.UnitTestApplicationImpl
import com.rodrigodev.common.rx.advanceTimeBy
import com.rodrigodev.common.spec.story.converter.DurationConverter
import com.rodrigodev.common.spec.story.steps.SpecSteps
import com.rodrigodev.common.testing.testSubscribe
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.jbehave.core.steps.ParameterConverters
import org.joda.time.Duration
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 23/10/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class DataRefreshRateIsThrottled : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<DataRefreshRateIsThrottled>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val testDataRepositoryManager: TestRepositoryManager<TestData>,
            private val testDataServices: TestDataServices,
            private val testDataObserver: EntityObserver<TestData>,
            private val testScheduler: TestScheduler,
            private val testTimeService: TestTimeServiceImpl
    ) : SpecSteps() {

        private lateinit var totalCountSubscriber: TestSubscriber<Long>

        override val converters = arrayOf<ParameterConverters.ParameterConverter>(DurationConverter())

        @Given("no data")
        fun givenNoData() {
            testDataRepositoryManager.clear()
        }

        @Given("a data refresh rate time of \$refreshRateTime with an initial offset of \$offset")
        fun aDataRefreshRateTimeOfXWithAnInitialOffsetOf(refreshRateTime: Duration, offset: Duration) {
            testTimeService.randomScalar = offset.millis.toDouble() / refreshRateTime.millis
        }

        @Given("I'm displaying the number of rows inserted")
        fun givenImDisplayingTheNumberOfRowsInserted() {
            totalCountSubscriber = testDataObserver.observeTotalCount().testSubscribe()
        }

        @When("after \$elapsedTime I insert [\$values]")
        fun whenAfterElapsedTimeIInsert(elapsedTime: Duration, values: MutableList<Int>) {
            testScheduler.advanceTimeBy(elapsedTime)
            values.forEach { testDataServices.execute(AddData(it)) }
        }

        @Then("after \$elapsedTime the values that have been displayed are [\$values]")
        fun thenAfterElapsedTimeTheValuesThatHaveBeenDisplayedAre(elapsedTime: Duration, values: MutableList<Long>) {
            testScheduler.advanceTimeBy(elapsedTime)
            assertThat(totalCountSubscriber.onNextEvents).containsExactlyElementsOf(values)
        }
    }
}