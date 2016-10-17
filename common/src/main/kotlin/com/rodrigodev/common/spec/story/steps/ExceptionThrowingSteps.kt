package com.rodrigodev.common.spec.story.steps

import com.rodrigodev.common.test.catchThrowable
import org.jbehave.core.annotations.BeforeScenario
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.ScenarioType
import rx.exceptions.OnErrorNotImplementedException

/**
 * Created by Rodrigo Quesada on 10/12/15.
 */
interface ExceptionThrowingSteps {

    var _thrownException: Throwable?
    var _catchException: Boolean

    val thrownException: Throwable?
        get() = _thrownException

    @BeforeScenario(uponType = ScenarioType.ANY)
    fun resetThrowable() {
        _thrownException = null
        _catchException = false
    }

    @Given("the next step might throw an exception")
    fun givenTheNextStepMightThrowAnException() {
        _catchException = true
    }

    fun mightThrowException(call: () -> Unit) {
        if (_catchException) {
            _thrownException = catchThrowable { call() }
            //TODO add exception unwrappers in order to decouple from RxJava
            _thrownException.let { e -> if (e is OnErrorNotImplementedException) _thrownException = e.cause ?: e }
            _catchException = false
        }
        else {
            _thrownException = null
            call()
        }
    }
}