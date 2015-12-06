@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.common.service.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.CommandImplementationVerification.TestApplicationImpl
import com.fasterxml.jackson.annotation.JsonProperty
import com.rodrigodev.common.spec.story.converter.JsonData
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 04/12/15.
 */
@Config(application = TestApplicationImpl::class)
internal class CommandImplementationVerification : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<CommandImplementationVerification>

    class TestApplicationImpl : TestApplication(DaggerCommandImplementationVerification_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
    ) {
        private lateinit var implementationVerifier: CommandImplementationVerifier

        @Given("I run the code that verifies the correct definition of entities on Command classes with the configuration \$configuration")
        fun givenIRunTheCodeThatVerifiesTheCorrectDefinitionOfEntitiesOnCommandClassesWithTheConfiguration(configuration: Configuration) {
            implementationVerifier = CommandImplementationVerifier(configuration.packageNames).apply { run() }
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

        @Then("the system detects there are commands under \$packageName and all are correctly implemented")
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