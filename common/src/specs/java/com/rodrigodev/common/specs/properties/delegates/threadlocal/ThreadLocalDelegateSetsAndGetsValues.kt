package com.rodrigodev.common.specs.properties.delegates.threadlocal

import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
import com.rodrigodev.common.properties.delegates.ThreadLocalDelegate
import com.rodrigodev.common.spec.story.SpecStory
import org.assertj.core.api.Assertions
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import kotlin.properties.Delegates.notNull

/**
* Created by Rodrigo Quesada on 26/10/15.
*/
internal class ThreadLocalDelegateSetsAndGetsValues : SpecStory() {

    override val steps = arrayOf(LocalSteps())

    class LocalSteps {
        private class TestProxy<T>(initialValueCall: () -> T) {
            private companion object {
                val DUMMY_CLEANER = ThreadLocalCleaner()
            }

            private val delegate = ThreadLocalDelegate(DUMMY_CLEANER, initialValueCall)
            var delegatedProperty: T by delegate
        }

        private var p: TestProxy<Int> by notNull()

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
            Assertions.assertThat(p.delegatedProperty).isEqualTo(value)
        }
    }
}