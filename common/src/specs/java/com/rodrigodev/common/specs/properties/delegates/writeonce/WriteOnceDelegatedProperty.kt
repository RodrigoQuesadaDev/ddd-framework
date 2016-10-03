package com.rodrigodev.common.specs.properties.delegates.writeonce

import com.rodrigodev.common.properties.Delegates
import com.rodrigodev.common.properties.delegates.WriteOnceDelegate
import com.rodrigodev.common.spec.story.SpecStory
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.test.catchThrowable
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When

/**
 * Created by Rodrigo Quesada on 02/10/16.
 */
internal class WriteOnceDelegatedProperty : SpecStory() {

    init {
        steps { listOf(LocalSteps()) }
    }

    class LocalSteps : ExceptionThrowingSteps {

        private lateinit var p: TestProxy<Int>

        override var throwable: Throwable? = null

        @Given("the property is initialized with the value \$value")
        fun givenThePropertyIsInitializedWithTheValue(value: Int) {
            p = TestProxy(value)
        }

        @Given("the property is not initialized")
        fun givenThePropertyIsNotInitialized() {
            p = TestProxy()
        }

        @When("the property is set to \$value")
        fun thePropertyIsSetTo(value: Int) {
            throwable = catchThrowable { p.delegatedProperty = value }
        }

        @When("the property is read")
        fun whenThePropertyIsRead() {
            throwable = catchThrowable { p.delegatedProperty }
        }

        @Then("the value of the property is \$value")
        fun theValueOfThePropertyIs(value: Int) {
            assertThat(throwable).isNull()
            assertThat(p.delegatedProperty).isEqualTo(value)
        }

        @Then("an IllegalStateException is thrown")
        fun thenAnIllegalStateExceptionIsThrown() {
            assertThat(throwable).isInstanceOf(IllegalStateException::class.java)
        }

        private class TestProxy<T : Any>(initialValue: T? = null) {

            private val delegate: WriteOnceDelegate<T>

            init {
                delegate = if (initialValue != null) Delegates.writeOnce(initialValue) else Delegates.writeOnce()
            }

            var delegatedProperty: T by delegate
        }
    }
}