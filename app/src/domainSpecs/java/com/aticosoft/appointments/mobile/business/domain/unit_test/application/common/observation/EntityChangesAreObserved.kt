package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.specs.common.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.*
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestDataRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.EntityChangesAreObserved.TestApplicationImpl
import com.rodrigodev.common.spec.story.SpecSteps
import com.rodrigodev.common.testing.subscribe
import dagger.Component
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.robolectric.annotation.Config
import rx.observers.TestSubscriber
import rx.schedulers.TestScheduler
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates.notNull

/**
 * Created by rodrigo on 31/10/15.
 */
@Config(application = TestApplicationImpl::class)
internal class EntityChangesAreObserved : DomainStory() {

    @Inject protected lateinit var specSteps: Steps

    override val steps by lazy { arrayOf(specSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<EntityChangesAreObserved>

    class TestApplicationImpl : TestApplication(DaggerEntityChangesAreObserved_TestApplicationComponentImpl::class)

    class Steps @Inject constructor(
            private val testDataObserver: TestDataObserver,
            private val testDataRepositoryManager: TestDataRepositoryManager,
            private val testDataServices: TestDataServices,
            private val testScheduler: TestScheduler
    ) : SpecSteps() {

        private val callbackListener = testDataObserver.entityListener
        private var testSubscriber: TestSubscriber<Int> by notNull()

        @Given("no data")
        fun givenNoData() {
            testDataRepositoryManager.clear()
        }

        @Given("I subscribe to the EntityCallbackListener")
        fun givenISubscribeToTheEntityCallbackListener() {
            testSubscriber = subscribe(callbackListener.changes.map { it.value })
        }

        @When("{after that |}I insert [\$values]")
        fun whenAfterThatIInsert(values: MutableList<Int>) {
            values.forEach { testDataServices.insertData.execute(InsertData.Command(it)) }
        }

        @When("{after that |}I delete [\$values]")
        fun whenAfterThatIDelete(values: MutableList<Int>) {
            values.forEach { testDataServices.deleteData.execute(DeleteData.Command(it)) }
        }

        @When("{after that |}I change \$currentValue into \$targetValue")
        fun whenAfterThatIChangeXInto(currentValue: Int, targetValue: Int) {
            testDataServices.changeData.execute(ChangeData.Command(currentValue, targetValue))
        }

        @Then("the entity changes observed were [\$values]")
        fun thenTheEntityChangesObservedWere(values: MutableList<Int>) {
            testScheduler.triggerActions()
            testSubscriber.assertValues(*values.toTypedArray())
        }
    }
}