@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.validation.classNames
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.validation.containedUnder
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.validation.invalid
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.validation.valid
import com.aticosoft.appointments.mobile.business.domain.testing.model.common.value_object.ValueObjectImplementationVerifier
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.ValueObjectImplementationVerification.UnitTestApplicationImpl
import com.fasterxml.jackson.annotation.JsonProperty
import com.rodrigodev.common.spec.story.converter.JsonData
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 04/10/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class ValueObjectImplementationVerification : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<ValueObjectImplementationVerification>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
    ) {
        private lateinit var implementationVerifier: ValueObjectImplementationVerifier

        @Given("I run the code that verifies the correct definition of ValueObject classes with the configuration \$configuration")
        fun givenIRunTheCodeThatVerifiesTheCorrectDefinitionOfEntitiesOnCommandClassesWithTheConfiguration(configuration: Configuration) {
            implementationVerifier = ValueObjectImplementationVerifier(configuration.packageNames).apply { run() }
        }

        @Then("the system detects that the next classes are not correctly implemented: \$classes")
        fun thenTheSystemDetectsThatOnlyTheNextClassesAreNotCorrectlyImplemented(classes: MutableList<ClassExample>) {
            theSystemDetectsTheNextClassesAre(false, classes)
        }

        @Then("the system detects that the next classes are correctly implemented: \$classes")
        fun theSystemDetectsTheNextClassesAreCorrectlyImplemented(classes: MutableList<ClassExample>) {
            theSystemDetectsTheNextClassesAre(true, classes)
        }

        private fun theSystemDetectsTheNextClassesAre(correctlyImplemented: Boolean, classes: MutableList<ClassExample>) {
            val results = with(implementationVerifier.results) {
                if (correctlyImplemented) valid() else invalid()
            }
            assertThat(results.classNames()).containsAll(classes.classNames())
        }

        @Then("the system detects there are value objects under \$packageName and all are correctly implemented")
        fun thenTheSystemDetectsThereIsCommandsUnderPackageAndAllAreCorrectlyImplemented(packageName: String) {
            val results = implementationVerifier.results.containedUnder(packageName)
            assertThat(results).isNotEmpty()
            assertThat(results.all { it.valid }).isTrue()
        }
    }
}

// For some reason single argument constructors do not get its arguments names detected automatically
// (using Paranamer plugin). Therefore I'm using @JsonProperty here.
@JsonData
internal data class Configuration(@JsonProperty("packageNames") val packageNames: Array<String>)

@AsParameters
internal class ClassExample {
    lateinit var className: String
}

private inline fun List<ClassExample>.classNames() = map { it.className }