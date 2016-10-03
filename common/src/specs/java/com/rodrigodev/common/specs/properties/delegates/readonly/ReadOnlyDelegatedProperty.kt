package com.rodrigodev.common.specs.properties.delegates.readonly

import com.rodrigodev.common.properties.Delegates
import com.rodrigodev.common.spec.story.SpecStory
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When

/**
 * Created by Rodrigo Quesada on 02/10/16.
 */
internal class ReadOnlyDelegatedProperty : SpecStory() {

    init {
        steps { listOf(LocalSteps()) }
    }

    class LocalSteps : ExceptionThrowingSteps {

        private lateinit var p: TestProxy<Int>

        override var _thrownException: Throwable? = null
        override var _catchException: Boolean = false

        @Given("the property is initialized with the value \$value")
        fun givenThePropertyIsInitializedWithTheValue(value: Int) {
            p = TestProxy(value)
        }

        @When("the property is set to \$value")
        fun thePropertyIsSetTo(value: Int) {
            mightThrowException { p.delegatedProperty = value }
        }

        @Then("the value of the property is \$value")
        fun theValueOfThePropertyIs(value: Int) {
            assertThat(p.delegatedProperty).isEqualTo(value)
        }

        @Then("an UnsupportedOperationException is thrown")
        fun thenAnUnsupportedOperationExceptionIsThrown() {
            assertThat(thrownException).isInstanceOf(UnsupportedOperationException::class.java)
        }

        private class TestProxy<T>(initialValue: T) {
            private val delegate = Delegates.readOnly(initialValue)
            var delegatedProperty: T by delegate
        }
    }
}