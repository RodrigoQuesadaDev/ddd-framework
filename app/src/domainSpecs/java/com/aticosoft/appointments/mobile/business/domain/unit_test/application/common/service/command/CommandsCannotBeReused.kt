package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.ReusedCommandException
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.CommandsCannotBeReused.LocalTestDataServices.AddData
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.CommandsCannotBeReused.LocalTestDataServices.AddNestedData
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.CommandsCannotBeReused.TestApplicationImpl
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.test.catchThrowable
import dagger.Component
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 09/12/15.
 */
@Config(application = TestApplicationImpl::class)
internal class CommandsCannotBeReused : DomainStory() {

    @Inject protected lateinit var localSteps: LocalSteps

    override val steps by lazy { arrayOf(localSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<CommandsCannotBeReused>

    class TestApplicationImpl : TestApplication(DaggerCommandsCannotBeReused_TestApplicationComponentImpl::class.java)

    class LocalSteps @Inject constructor(
            private val testDataServices: LocalTestDataServices
    ) : ExceptionThrowingSteps {

        override var throwable: Throwable? = null
        private lateinit var command: AddNestedData

        @Given("I create a command object and pass it to an application service")
        fun givenICreateACommandObjectAndPassItToAnApplicationService() {
            command = AddNestedData(AddData(123))
            testDataServices.execute(command)
        }

        @When("I reuse it a second time on another call")
        fun whenIReuseItASecondTimeOnAnotherCall() {
            throwable = catchThrowable { testDataServices.execute(command) }
        }

        @When("I reuse one of its nested commands on a different call")
        fun whenIReuseOneOfItsNestedCommandsOnADifferentCall() {
            throwable = catchThrowable { testDataServices.execute(command.nested) }
        }

        @Then("the system throws an exception indicating the command has already being used")
        fun thenTheSystemThrowsAnExceptionIndicatingTheCommandHasAlreadyBeingUsed() {
            assertThat(throwable).isInstanceOf(ReusedCommandException::class.java)
        }
    }

    @Singleton
    class LocalTestDataServices @Inject constructor(private val c: LocalTestDataServices.Context) : ApplicationServices(c.superContext) {

        class AddNestedData(nested: AddData) : Command() {
            val nested by nested.delegate()
        }

        class AddData(val value: Int) : Command()

        fun execute(command: AddNestedData) = command.execute {
            nested //just "use" property by reading it
        }

        fun execute(command: AddData) = command.execute {
            // Do nothing!
        }

        class Context @Inject constructor(val superContext: ApplicationServices.Context)
    }
}