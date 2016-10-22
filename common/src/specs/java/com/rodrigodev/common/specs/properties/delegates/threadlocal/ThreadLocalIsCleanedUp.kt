package com.rodrigodev.common.specs.properties.delegates.threadlocal

import com.rodrigodev.common.properties.Delegates
import com.rodrigodev.common.properties.delegates.SafeThreadLocalCleaner
import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
import com.rodrigodev.common.spec.story.SpecStory
import com.rodrigodev.common.specs.properties.delegates.threadlocal.ThreadLocalIsCleanedUpSteps.ThreadLocalCleanerType.SAFE
import com.rodrigodev.common.specs.properties.delegates.threadlocal.ThreadLocalIsCleanedUpSteps.ThreadLocalCleanerType.UNSAFE
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When

/**
 * Created by Rodrigo Quesada on 26/10/15.
 */
internal class ThreadLocalIsCleanedUpSteps {

    private class DelegatedPropertyContainer<T>(cleaner: ThreadLocalCleaner, initialValueCall: () -> T) {

        private val delegate = Delegates.threadLocal(cleaner, initialValueCall)
        var delegatedProperty: T by delegate
    }

    private lateinit var cleaner: ThreadLocalCleaner
    private lateinit var c: DelegatedPropertyContainer<Int>
    private val registeredDelegatedProperties = arrayListOf<DelegatedPropertyContainer<Int>>()

    @Given("dummy step")
    fun givenDummyStep() {
    }

    @Given("a \$threadLocalCleanerType ThreadLocalCleaner object is created")
    fun givenACleanUpObjectIsCreated(threadLocalCleanerType: ThreadLocalCleanerType) {
        cleaner = when (threadLocalCleanerType) {
            SAFE -> SafeThreadLocalCleaner()
            UNSAFE -> ThreadLocalCleaner()
        }
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

    enum class ThreadLocalCleanerType { SAFE, UNSAFE }
}

internal class ThreadLocalIsCleanedUpBySafeCleaner : SpecStory() {

    init {
        steps { listOf(ThreadLocalIsCleanedUpSteps()) }
    }
}

internal class ThreadLocalIsCleanedUpByUnsafeCleaner : SpecStory() {

    init {
        steps { listOf(ThreadLocalIsCleanedUpSteps()) }
    }
}