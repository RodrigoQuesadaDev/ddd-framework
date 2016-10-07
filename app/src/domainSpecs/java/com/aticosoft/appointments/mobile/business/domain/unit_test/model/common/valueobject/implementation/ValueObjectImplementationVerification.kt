@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.classNames
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.containedUnder
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.invalid
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.valid
import com.aticosoft.appointments.mobile.business.domain.testing.model.common.valueobject.ValueObjectImplementationVerifier
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.ClassExample
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.Configuration
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.classNames
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.ValueObjectImplementationVerification.UnitTestApplicationImpl
import org.assertj.core.api.Assertions.assertThat
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

    class LocalSteps @Inject constructor() {

        private lateinit var implementationVerifier: ValueObjectImplementationVerifier

        @Given("I run the code that verifies the correct definition of ValueObject classes with the next configuration: \$configuration")
        fun givenIRunTheCodeThatVerifiesTheCorrectDefinitionOfValueObjectClassesWithTheNextConfiguration(configuration: Configuration) {
            implementationVerifier = ValueObjectImplementationVerifier(configuration.packageNames).apply { run() }
        }

        @Then("the system detects that only the next classes are not correctly implemented: \$classes")
        fun thenTheSystemDetectsThatOnlyTheNextClassesAreNotCorrectlyImplemented(classes: MutableList<ClassExample>) {
            assertThat(implementationVerifier.results.invalid().classNames()).containsOnlyElementsOf(classes.classNames())
        }

        @Then("the system detects that the next classes are correctly implemented: \$classes")
        fun theSystemDetectsTheNextClassesAreCorrectlyImplemented(classes: MutableList<ClassExample>) {
            assertThat(implementationVerifier.results.valid().classNames()).containsAll(classes.classNames())
        }

        @Then("the system detects there are value objects under \$packageName and all are correctly implemented")
        fun thenTheSystemDetectsThereAreValueObjectsUnderPackageAndAllAreCorrectlyImplemented(packageName: String) {
            val results = implementationVerifier.results.containedUnder(packageName)
            assertThat(results).isNotEmpty()
            assertThat(results.all { it.valid }).isTrue()
        }
    }
}