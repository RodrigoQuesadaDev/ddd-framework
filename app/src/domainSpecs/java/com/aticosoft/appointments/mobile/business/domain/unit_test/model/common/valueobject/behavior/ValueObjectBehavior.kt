@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior

import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.exceptions.ValueObjectModificationException
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior.ValueObjectBehavior.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior.test_data.TestDataServices.*
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 04/10/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class ValueObjectBehavior : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<ValueObjectBehavior>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val testDataServices: TestDataServices
    ) : ExceptionThrowingSteps {

        override var _thrownException: Throwable? = null
        override var _catchException: Boolean = false

        @Given("a value object is created with value \$value")
        fun givenAValueObjectIsCreated(value: Int) {
            testDataServices.execute(CreateValueObject(value))
        }

        @When("a value object is created with value \$value and then immediately modified")
        fun whenAValueObjectIsCreatedAndThenImmediatelyModified(value: Int) = mightThrowException {
            testDataServices.execute(CreateAndThenModified(value))
        }

        @When("the value object with value \$value is retrieved from the database and then modified")
        fun whenTheValueObjectIsQueriedAndThenModified(value: Int) = mightThrowException {
            testDataServices.execute(FindAndThenModified(value))
        }

        @Then("the system throws a ValueObjectModificationException")
        fun thenTheSystemThrowsAValueObjectModificationException() {
            assertThat(thrownException).isInstanceOf(ValueObjectModificationException::class.java)
        }
    }
}