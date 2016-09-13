package com.rodrigodev.common.specs.rx.observables

import com.rodrigodev.common.rx.advanceTimeBy
import com.rodrigodev.common.rx.testing.RxSpec
import com.rodrigodev.common.rx.throttleLast
import com.rodrigodev.common.spec.story.converter.DurationConverter
import com.rodrigodev.common.spec.story.steps.SpecSteps
import com.rodrigodev.common.rx.testing.testSubscribe
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.jbehave.core.steps.ParameterConverters.ParameterConverter
import org.joda.time.Duration
import rx.lang.kotlin.PublishSubject
import rx.observers.TestSubscriber

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
internal class ThrottleLastTest : RxSpec() {

    init {
        steps { listOf(LocalSteps()) }
    }

    inner class LocalSteps : SpecSteps() {

        override val converters: Array<ParameterConverter> = arrayOf(DurationConverter())

        private var sourceObservable = PublishSubject<Int>()
        private lateinit var subscriber: TestSubscriber<Int>

        @Given("I subscribe to an observable using throttleLast with initialIntervalDuration=\$initialDuration and intervalDuration=\$duration")
        fun givenISubscribeToAnObservableUsingThrottleLast(initialDuration: Duration, duration: Duration) {
            subscriber = sourceObservable.throttleLast(initialDuration, duration).testSubscribe()
        }

        @When("after \$elapsedTime the source observable emits [\$values]")
        fun whenAfterElapsedTimeTheSourceObservableEmits(elapsedTime: Duration, values: MutableList<Int>) {
            testScheduler.advanceTimeBy(elapsedTime)
            values.forEach { sourceObservable.onNext(it) }
        }

        @Then("after \$elapsedTime emitted values are [\$values]")
        fun thenAfterElapsedTimeEmittedValuesAre(elapsedTime: Duration, values: MutableList<Int>) {
            testScheduler.advanceTimeBy(elapsedTime)
            subscriber.assertValues(*values.toTypedArray())
        }
    }
}