package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.ValidationException
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.EntitiesAreValidated.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.EntityType.CHILD
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.EntityType.PARENT
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.ValidationExceptionType.CONSTRAINT_VIOLATION
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.ValidationExceptionType.VALIDATION_EXCEPTION
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.ValidationType.ODD_VALUE_AND_EMAIL
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.ValidationType.PRIME_NUMBER_AND_GMAIL
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.TestDataServices.*
import com.rodrigodev.common.spec.story.converter.JsonData
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.test.catchThrowable
import com.rodrigodev.common.testing.firstEvent
import com.rodrigodev.common.testing.testSubscribe
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.*
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.validation.ConstraintViolationException

/**
 * Created by Rodrigo Quesada on 11/12/15.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class EntitiesAreValidated : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<EntitiesAreValidated>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val oddValueAndEmailParentRepositoryManager: OddValueAndEmailParentRepositoryManager,
            private val oddValueAndEmailParentObserver: EntityObserver<OddValueAndEmailParent>,
            private val oddValueAndEmailParentQueries: OddValueAndEmailParentQueries,
            private val primeNumberAndGmailParentRepositoryManager: PrimeNumberAndGmailParentRepositoryManager,
            private val primeNumberAndGmailParentObserver: EntityObserver<PrimeNumberAndGmailParent>,
            private val primeNumberAndGmailParentQueries: PrimeNumberAndGmailParentQueries,
            private val testDataServices: TestDataServices
    ) : ExceptionThrowingSteps {

        private object ValidValues {
            val ODD_VALUE = 3
            val PRIME_VALUE = 7
            val EMAIL = "test@email.com"
            val GMAIL_EMAIL = "test@gmail.com"
            val EXTRA_VALUE = 0
        }

        override var throwable: Throwable? = null

        private var validatedEntityInfo: EntityInfo? = null

        @BeforeScenario(uponType = ScenarioType.ANY)
        fun resetValidEntity() {
            validatedEntityInfo = null
        }

        @AfterScenario(uponType = ScenarioType.ANY)
        fun checkThrowableIsNull() {
            assertThat(throwable).isNull();
        }

        private fun updateValidatedEntityInfo(entityType: EntityType, validationType: ValidationType) {
            validatedEntityInfo = EntityInfo(entityType, validationType)
        }

        private fun clearValidatorsRunInfo() {
            ValidatorsRunInfo.clear()
        }

        @Given("no data")
        fun givenNoData() {
            oddValueAndEmailParentRepositoryManager.clear()
            primeNumberAndGmailParentRepositoryManager.clear()
        }

        @Given("a valid \$entityType entity with \$validationType validation")
        fun givenAValidEntityWithValidation(entityType: EntityType, validationType: ValidationType) {
            givenNoData()
            whenAnEntityWithValidationIsCreated(entityType, validationType, validTestDataValues())
            updateValidatedEntityInfo(entityType, validationType)
        }

        @When("an \$entityType entity with \$validationType validation is created with the values \$testDataValues")
        fun whenAnEntityWithValidationIsCreated(entityType: EntityType, validationType: ValidationType, testDataValues: TestDataValues) {
            clearValidatorsRunInfo()
            throwable = catchThrowable {
                when (entityType) {
                    PARENT -> when (validationType) {
                        ODD_VALUE_AND_EMAIL -> testDataServices.execute(AddOddValueAndEmailParent(testDataValues.number, testDataValues.email, ValidValues.ODD_VALUE, ValidValues.EMAIL))
                        PRIME_NUMBER_AND_GMAIL -> testDataServices.execute(AddPrimeNumberAndGmailParent(testDataValues.number, testDataValues.email, ValidValues.EXTRA_VALUE, ValidValues.PRIME_VALUE, ValidValues.GMAIL_EMAIL, ValidValues.EXTRA_VALUE))
                    }
                    CHILD -> when (validationType) {
                        ODD_VALUE_AND_EMAIL -> testDataServices.execute(AddOddValueAndEmailParent(ValidValues.ODD_VALUE, ValidValues.EMAIL, testDataValues.number, testDataValues.email))
                        PRIME_NUMBER_AND_GMAIL -> testDataServices.execute(AddPrimeNumberAndGmailParent(ValidValues.PRIME_VALUE, ValidValues.GMAIL_EMAIL, ValidValues.EXTRA_VALUE, testDataValues.number, testDataValues.email, ValidValues.EXTRA_VALUE))
                    }
                }
            }
            updateValidatedEntityInfo(entityType, validationType)
        }

        @When("the entity is updated with the values \$testDataValues")
        fun whenTheEntityIsUpdated(testDataValues: TestDataValues) {
            clearValidatorsRunInfo()
            throwable = catchThrowable {
                with(validatedEntityInfo!!) {
                    when (entityType) {
                        PARENT -> when (validationType) {
                            ODD_VALUE_AND_EMAIL -> testDataServices.execute(UpdateOddValueAndEmailParent(testDataValues.number, testDataValues.email))
                            PRIME_NUMBER_AND_GMAIL -> testDataServices.execute(UpdatePrimeNumberAndGmailParent(testDataValues.number, testDataValues.email))
                        }
                        CHILD -> when (validationType) {
                            ODD_VALUE_AND_EMAIL -> testDataServices.execute(UpdateOddValueAndEmailChild(testDataValues.number, testDataValues.email))
                            PRIME_NUMBER_AND_GMAIL -> testDataServices.execute(UpdatePrimeNumberAndGmailChild(testDataValues.number, testDataValues.email))
                        }
                    }
                }
            }
        }

        @When("one of the entity's fields that are not validated is updated")
        fun whenOneOfTheEntitysFieldsThatAreNotValidatedIsUpdated() {
            clearValidatorsRunInfo()
            throwable = catchThrowable {
                with(validatedEntityInfo!!) {
                    when (entityType) {
                        PARENT -> testDataServices.execute(UpdateExtraValueForPrimeNumberAndGmailParent(ValidValues.EXTRA_VALUE + 1))
                        CHILD -> testDataServices.execute(UpdateExtraValueForPrimeNumberAndGmailChild(ValidValues.EXTRA_VALUE + 1))
                    }
                }
            }
        }

        @Then("the system throws a \$validationExceptionType with the message \"\$errorMessage\"")
        fun thenTheSystemThrowsAConstraintViolationExceptionWithTheMessage(validationExceptionType: ValidationExceptionType, errorMessage: String) {
            when (validationExceptionType) {
                CONSTRAINT_VIOLATION -> {
                    assertThat(throwable).isInstanceOf(ConstraintViolationException::class.java)
                    assertThat((throwable as ConstraintViolationException).constraintViolations.first().message).isEqualTo(errorMessage)
                }
                VALIDATION_EXCEPTION -> {
                    assertThat(throwable).isInstanceOf(PrimeNumberAndGmailValidationException::class.java)
                    assertThat((throwable as ValidationException).message).isEqualTo(errorMessage)
                }
            }
            throwable = null
        }

        @Then("validation runs only once per each field/class it validates")
        fun thenValidationRunsOnlyOncePerEachFieldClassItValidates() {
            assertValidationRunsNTimesPerEachFieldClassItValidates(1)
        }

        fun assertValidationRunsNTimesPerEachFieldClassItValidates(n: Int) {
            assertThat(ValidatorsRunInfo.timesRun(with(validatedEntityInfo!!) {
                when (entityType) {
                    PARENT -> when (validationType) {
                        ODD_VALUE_AND_EMAIL -> OddValueValidator.validatorIdFor(OddValueAndEmailParent::class)
                        PRIME_NUMBER_AND_GMAIL -> PrimeNumberAndGmailValidator.validatorIdFor(PrimeNumberAndGmailParent::class)
                    }
                    CHILD -> when (validationType) {
                        ODD_VALUE_AND_EMAIL -> OddValueValidator.validatorIdFor(OddValueAndEmailChild::class)
                        PRIME_NUMBER_AND_GMAIL -> PrimeNumberAndGmailValidator.validatorIdFor(PrimeNumberAndGmailChild::class)
                    }
                }
            })).isEqualTo(n)
        }

        @Then("the system doesn't throw any validation exception")
        fun thenTheSystemDoesNotThrowAnyValidationException() {
            assertThat(throwable).isNull()
        }

        @Then("the entity is not updated")
        fun thenTheEntityIsNotUpdated() {
            fun NumberAndEmailTestData.assertIsNotUpdated() {
                val testDataValues = validTestDataValues()
                assertThat(number).isEqualTo(testDataValues.number)
                assertThat(email).isEqualTo(testDataValues.email)
            }

            with(validatedEntityInfo!!) {
                @Suppress("IMPLICIT_CAST_TO_UNIT_OR_ANY")
                (when (validationType) {
                    ODD_VALUE_AND_EMAIL -> oddValueAndEmailParentObserver.observe(oddValueAndEmailParentQueries.first(), OddValueAndEmailParentQueryView.ALL).testSubscribe().firstEvent()
                    PRIME_NUMBER_AND_GMAIL -> primeNumberAndGmailParentObserver.observe(primeNumberAndGmailParentQueries.first(), PrimeNumberAndGmailParentQueryView.ALL).testSubscribe().firstEvent()
                } as NumberAndEmailTestDataParent<*>).let { entity ->
                    when (entityType) {
                        PARENT -> entity.assertIsNotUpdated()
                        CHILD -> entity.child.assertIsNotUpdated()
                    }
                }
            }
        }

        @Then("the validation doesn't run")
        fun thenTheValidationDoesNotRun() {
            assertValidationRunsNTimesPerEachFieldClassItValidates(0)
        }

        private fun validTestDataValues(): TestDataValues = TestDataValues(ValidValues.PRIME_VALUE, ValidValues.GMAIL_EMAIL)
    }
}

internal enum class EntityType {PARENT, CHILD }

internal enum class ValidationType {ODD_VALUE_AND_EMAIL, PRIME_NUMBER_AND_GMAIL }

internal enum class ValidationExceptionType {CONSTRAINT_VIOLATION, VALIDATION_EXCEPTION }

@JsonData
internal data class TestDataValues(
        val number: Int,
        val email: String
)

internal class EntityInfo(val entityType: EntityType, val validationType: ValidationType)