package com.rodrigodev.common.specs.properties.delegates.threadlocal

import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
import com.rodrigodev.common.properties.delegates.ThreadLocalDelegate
import com.rodrigodev.common.spec.story.SpecStory
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import kotlin.properties.Delegates.notNull

/**
* Created by Rodrigo Quesada on 26/10/15.
*/
internal class ThreadLocalIsCleanUp : SpecStory() {

    override val steps = arrayOf(LocalSteps())

    class LocalSteps {

        private class DelegatedPropertyContainer<T>(cleaner: ThreadLocalCleaner, initialValueCall: () -> T) {

            private val delegate = ThreadLocalDelegate(cleaner, initialValueCall)
            var delegatedProperty: T by delegate
        }

        private var cleaner: ThreadLocalCleaner by notNull()
        private var c: DelegatedPropertyContainer<Int> by notNull()
        private val registeredDelegatedProperties = arrayListOf<DelegatedPropertyContainer<Int>>()

        @Given("a ThreadLocalCleaner object is created")
        fun givenACleanUpObjectIsCreated() {
            cleaner = ThreadLocalCleaner()
        }

        @Given("a ThreadLocalDelegate that is initialized with a value of 0 and is registered with the ThreadLocalCleaner object")
        fun givenAThreadLocalDelegateThatIsInitializedWithAValueOfXAndIsRegisteredWithTheCleanUpObject() {
            c = DelegatedPropertyContainer(cleaner) { 0 }
            registeredDelegatedProperties.add(c)
        }

        @When("the value of the delegated property is set to \$value")
        fun whenTheValueOfTheDelegatedPropertyIsSetToX(value: Int) {
            c.delegatedProperty = value
        }

        @Then("the number of registered ThreadLocal objects is \$number")
        fun thenTheNumberOfRegisteredThreadLocalObjectsIsX(number: Int) {
            assertThat(registeredDelegatedProperties).hasSize(number)
        }

        @When("the cleanUpThreadLocalInstances method of the ThreadLocalCleaner object is called")
        fun whenTheResetLocalStateMethodOfTheCleanUpObjectIsCalled() {
            cleaner.cleanUpThreadLocalInstances()
        }

        @Then("the registered ThreadLocal objects were clean-up")
        fun thenTheRegisteredThreadLocalObjectsWereCleanUp() {
            registeredDelegatedProperties.forEach { assertThat(it.delegatedProperty).isEqualTo(0) }
        }
    }
}