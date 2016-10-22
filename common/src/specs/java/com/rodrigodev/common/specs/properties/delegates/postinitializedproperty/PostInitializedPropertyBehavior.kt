@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.specs.properties.delegates.postinitializedproperty

import com.rodrigodev.common.properties.Delegates.postInitialized
import com.rodrigodev.common.properties.PostInitializedPropertyType.UNSAFE
import com.rodrigodev.common.properties.delegates.UnsafePostInitializedPropertyDelegate.UnsafePropertyInitializer
import com.rodrigodev.common.spec.story.SpecStory
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.specs.properties.delegates.postinitializedproperty.PostInitializedPropertyBehavior.LocalSteps.StepMessages.DEFINED_PROPERTY_IS_INITIALIZED
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.*

/**
 * Created by Rodrigo Quesada on 26/10/15.
 */
internal class PostInitializedPropertyBehavior : SpecStory() {

    init {
        steps { listOf(LocalSteps()) }
    }

    class LocalSteps : ExceptionThrowingSteps {
        object StepMessages {
            const val DEFINED_PROPERTY_IS_INITIALIZED = "the defined property is initialized by the PropertyInitializer"
        }

        private lateinit var propertyInitializer: UnsafePropertyInitializer
        private val definedProperties = mutableListOf<PropertyContainer<Int>>()

        override var _thrownException: Throwable? = null
        override var _catchException: Boolean = false

        @BeforeScenario(uponType = ScenarioType.ANY)
        fun resetDefinedProperties() {
            definedProperties.clear()
        }

        @Given("a PropertyInitializer object is created")
        fun givenAPropertyInitializerIsCreated() {
            propertyInitializer = UnsafePropertyInitializer()
        }

        @Given("a property delegated by an PostInitializedPropertyDelegate is defined with a value of \$value")
        fun givenAPropertyDelegatedByAPostInitializedPropertyDelegateIsDefinedWithAValue(value: Int) {
            definedProperties.add(PropertyContainer(propertyInitializer, { value }))
        }

        @Given("some properties delegated by an PostInitializedPropertyDelegate are defined with the values: [\$values]")
        fun givenSomePropertiesDelegatedByAnPostInitializedPropertyDelegateAreDefinedWithTheValues(values: MutableList<Int>) = values.forEach {
            givenAPropertyDelegatedByAPostInitializedPropertyDelegateIsDefinedWithAValue(it)
        }

        @When("the value of the defined property is read")
        fun whenTheValueOfTheDefinedPropertyIsRead() {
            mightThrowException { definedProperties.single().property }
        }

        @Given(DEFINED_PROPERTY_IS_INITIALIZED)
        @When(DEFINED_PROPERTY_IS_INITIALIZED)
        fun theDefinedPropertyIsInitializedByThePropertyInitializer() {
            mightThrowException { propertyInitializer.init() }
        }

        @When("the defined properties are initialized by the PropertyInitializer")
        fun whenTheDefinedPropertiesAreInitializedByThePropertyInitializer() {
            propertyInitializer.init()
        }

        @Then("an IllegalStateException indicating the property hasn't been initialized yet is thrown")
        fun thenAnExceptionIndicatingThePropertyHasNotBeenInitializedYetIsThrown() {
            assertThat(thrownException).isInstanceOf(IllegalStateException::class.java)
        }

        @Then("an IllegalStateException indicating the property has already been initialized is thrown")
        fun thenAnExceptionIndicatingThePropertyHasAlreadyBeenInitializedIsThrown() {
            assertThat(thrownException).isInstanceOf(IllegalStateException::class.java)
        }

        @Then("the read value of the defined properties are: [\$values]")
        fun thenTheReadValueOfTheDefinedPropertiesAre(values: MutableList<Int>) {
            assertThat(definedProperties.map { it.property }).containsExactlyElementsOf(values)
        }

        private class PropertyContainer<out T : Any>(propertyInitializer: UnsafePropertyInitializer, initialValueCall: () -> T) {

            val property: T by postInitialized(UNSAFE, propertyInitializer, initialValueCall)
        }
    }
}