package com.aticosoft.appointments.mobile.business.domain.specs.infrastructure.display.refresh_rate

import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.InsertData
import com.aticosoft.appointments.mobile.business.domain.testing.common.time.TestTimeService
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.rodrigodev.common.rx.advanceTimeBy
import com.rodrigodev.common.spec.story.SpecSteps
import com.rodrigodev.common.spec.story.converter.DurationConverter
import com.rodrigodev.common.testing.subscribe
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.jbehave.core.steps.ParameterConverters.ParameterConverter
import org.joda.time.Duration
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

/**
 * Created by rodrigo on 23/10/15.
 */
@Singleton
internal class DataRefreshRateSteps @Inject constructor(
        private val testDataRepositoryManager: TestDataRepositoryManager,
        private val testDataServices: TestDataServices,
        private val testDataObserver: TestDataObserver,
        private val testScheduler: TestScheduler,
        private val testTimeService: TestTimeService
) : SpecSteps() {

    private var sizeSubscriber: TestSubscriber<Long> by notNull()

    override val converters = arrayOf<ParameterConverter>(DurationConverter())

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
        sizeSubscriber = subscribe(testDataObserver.observeSize())
    }

    @When("after \$elapsedTime I insert [\$values]")
    fun whenAfterElapsedTimeIInsert(elapsedTime: Duration, values: MutableList<Int>) {
        testScheduler.advanceTimeBy(elapsedTime)
        values.forEach { testDataServices.insertData.execute(InsertData.Command(it)) }
    }

    @Then("after \$elapsedTime the values that have been displayed are [\$values]")
    fun thenAfterElapsedTimeTheValuesThatHaveBeenDisplayedAre(elapsedTime: Duration, values: MutableList<Long>) {
        testScheduler.advanceTimeBy(elapsedTime)
        sizeSubscriber.assertValues(*values.toTypedArray())
    }
}