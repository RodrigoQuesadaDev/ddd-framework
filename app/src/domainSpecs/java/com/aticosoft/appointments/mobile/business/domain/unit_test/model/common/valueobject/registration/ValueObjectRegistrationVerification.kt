@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.registration

import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.ValueObject
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.classNames
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.containedUnder
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.notRegistered
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.registered
import com.aticosoft.appointments.mobile.business.domain.testing.model.common.valueobject.ValueObjectRegistrationVerifier
import com.aticosoft.appointments.mobile.business.domain.testing.model.common.valueobject.ValueObjectRegistrationVerifierFactory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.ClassExample
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.Configuration
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.classNames
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.registration.ValueObjectRegistrationVerification.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.valueobject.ValueObjectsManager
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Condition
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 04/10/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class ValueObjectRegistrationVerification : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<ValueObjectRegistrationVerification>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val registrationVerifierFactory: ValueObjectRegistrationVerifierFactory,
            private val valueObjectsManager: ValueObjectsManager
    ) {

        private lateinit var registrationVerifier: ValueObjectRegistrationVerifier

        @Given("I run the code that verifies ValueObject classes are registered by the framework, using the next configuration: \$configuration")
        fun givenIRunTheCodeThatVerifiesValueObjectClassesAreRegisteredByTheFrameworkUsingTheNextConfiguration(configuration: Configuration) {
            registrationVerifier = registrationVerifierFactory.create(configuration.packageNames).apply { run() }
        }

        @Then("the system detects that only the next classes are not registered: \$classes")
        fun thenTheSystemDetectsThatOnlyTheNextClassesAreNotRegistered(classes: MutableList<ClassExample>) {
            assertThat(registrationVerifier.results.notRegistered().classNames()).containsOnlyElementsOf(classes.classNames())
        }

        @Then("the system detects that the next classes are correctly registered: \$classes")
        fun thenTheSystemDetectsThatTheNextClassesAreCorrectlyRegistered(classes: MutableList<ClassExample>) {
            assertThat(registrationVerifier.results.registered().classNames()).containsAll(classes.classNames())
        }

        @Then("the system detects that all classes registered as value objects are effectively value objects")
        fun thenTheSystemDetectsThatAllClassesRegisteredAsValueObjectsAreEffectivelyValueObjects() {
            assertThat(valueObjectsManager.registeredValueObjects).are(ValueObjectClass())
        }

        @Then("the system detects there are value objects under \$packageName and all are correctly registered")
        fun thenTheSystemDetectsThereAreValueObjectsUnderPackageAndAllAreCorrectlyRegistered(packageName: String) {
            val results = registrationVerifier.results.containedUnder(packageName)
            assertThat(results).isNotEmpty()
            assertThat(results.all { it.registered }).isTrue()
        }
    }
}

//region Assertions
private class ValueObjectClass : Condition<Class<*>>() {

    override fun matches(clazz: Class<*>) = clazz.getAnnotation(ValueObject::class.java) != null
}
//endregion