package com.rodrigodev.common.specs.properties.delegates.threadlocal

import com.rodrigodev.common.properties.Delegates
import com.rodrigodev.common.properties.delegates.SafeThreadLocalCleaner
import com.rodrigodev.common.spec.story.SpecStory
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When

/**
 * Created by Rodrigo Quesada on 26/10/15.
 */
internal class ThreadLocalDelegateSetsAndGetsValues : SpecStory() {

    init {
        steps { listOf(LocalSteps()) }
    }

    class LocalSteps {
        private lateinit var p: TestProxy<Int>

        @Given("a ThreadLocalDelegate that is initialized with a value of \$value")
        fun givenAThreadLocalDelegateThatIsInitializedWithAValueOfX(value: Int) {
            p = TestProxy { value }
        }

        @When("the value of the delegated property is set to \$value")
        fun theValueOfTheDelegatedPropertyIsSetToX(value: Int) {
            p.delegatedProperty = value
        }

        @Then("the value of the delegated property is \$value")
        fun thenTheValueOfTheDelegatedPropertyIsX(value: Int) {
            assertThat(p.delegatedProperty).isEqualTo(value)
        }

        private class TestProxy<T>(initialValueCall: () -> T) {
            private companion object {
                val DUMMY_CLEANER = SafeThreadLocalCleaner()
            }

            private val delegate = Delegates.threadLocal(DUMMY_CLEANER, initialValueCall)
            var delegatedProperty: T by delegate
        }
    }
}