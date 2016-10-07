@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.application.common.service.command.CommandImplementationVerifier
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.classNames
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.containedUnder
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.invalid
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.valid
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.CommandImplementationVerification.UnitTestApplicationImpl
import com.fasterxml.jackson.annotation.JsonProperty
import com.rodrigodev.common.spec.story.converter.JsonData
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 04/12/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class CommandImplementationVerification : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<CommandImplementationVerification>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor() {

        private lateinit var implementationVerifier: CommandImplementationVerifier

        @Given("I run the code that verifies the correct definition of entities on Command classes with the next configuration: \$configuration")
        fun givenIRunTheCodeThatVerifiesTheCorrectDefinitionOfEntitiesOnCommandClassesWithTheNextConfiguration(configuration: Configuration) {
            implementationVerifier = CommandImplementationVerifier(configuration.packageNames).apply { run() }
        }

        @Then("the system detects that only the next classes are not correctly implemented: \$classes")
        fun thenTheSystemDetectsThatOnlyTheNextClassesAreNotCorrectlyImplemented(classes: MutableList<ClassExample>) {
            assertThat(implementationVerifier.results.invalid().classNames()).containsOnlyElementsOf(classes.classNames())
        }

        @Then("the system detects that the next classes are correctly implemented: \$classes")
        fun theSystemDetectsTheNextClassesAreCorrectlyImplemented(classes: MutableList<ClassExample>) {
            assertThat(implementationVerifier.results.valid().classNames()).containsAll(classes.classNames())
        }

        @Then("the system detects there are commands under \$packageName and all are correctly implemented")
        fun thenTheSystemDetectsThereAreCommandsUnderPackageAndAllAreCorrectlyImplemented(packageName: String) {
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