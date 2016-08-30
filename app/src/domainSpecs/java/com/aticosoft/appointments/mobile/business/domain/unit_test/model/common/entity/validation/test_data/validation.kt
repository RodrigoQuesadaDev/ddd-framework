@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidationException
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.querydsl.core.types.Path
import com.rodrigodev.common.testing.number.isPrime
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FIELD
import kotlin.reflect.KClass

/**
 * Created by Rodrigo Quesada on 02/01/16.
 */

/***************************************************************************************************
 * Odd Value Validation
 **************************************************************************************************/

@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = arrayOf(OddValueValidator::class))
@MustBeDocumented
internal annotation class OddValue(
        val entityType: KClass<out Entity>,
        val message: String = "{com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.OddValue}",
        val groups: Array<out KClass<*>> = arrayOf(),
        val payload: Array<out KClass<out Payload>> = arrayOf()
)

internal class OddValueValidator : ConstraintValidatorWithRunInfo<OddValue, Int>() {
    companion object {
        fun <E : Entity> validatorIdFor(entityType: KClass<E>) = ValidatorId(OddValue::class.java, entityType.java)
    }

    override fun initialize(constraintAnnotation: OddValue) {
        setupRunInfo(validatorIdFor(constraintAnnotation.entityType))
    }

    override fun doIsValid(value: Int, context: ConstraintValidatorContext): Boolean {
        return value % 2 != 0
    }
}

/***************************************************************************************************
 * Prime Number and Gmail Validation
 **************************************************************************************************/

internal abstract class PrimeNumberAndGmailValidator<E : PrimeNumberAndGmailValidatedTestData>(
        entityType: KClass<E>
) : PersistableObjectValidatorWithRunInfo<E, PrimeNumberAndGmailValidationException>(validatorIdFor(entityType),
        ::PrimeNumberAndGmailValidationException,
        QPrimeNumberAndGmailValidatedTestData.primeNumberAndGmailValidatedTestData.number,
        QPrimeNumberAndGmailValidatedTestData.primeNumberAndGmailValidatedTestData.email
) {
    companion object {
        fun <E : PrimeNumberAndGmailValidatedTestData> validatorIdFor(entityType: KClass<E>) = ValidatorId(PrimeNumberAndGmailValidator::class.java, entityType.java)
    }

    override fun E.errorMessage() = "either number '$number' is not prime or email '$email' is not from gmail"

    override fun E.doIsValid() = number.isPrime() && email.endsWith("gmail.com", true);
}

internal class PrimeNumberAndGmailValidationException(message: String) : PersistableObjectValidationException(message)

@Singleton
internal class PrimeNumberAndGmailParentValidator @Inject protected constructor() : PrimeNumberAndGmailValidator<PrimeNumberAndGmailParent>(PrimeNumberAndGmailParent::class)

@Singleton
internal class PrimeNumberAndGmailChildValidator @Inject protected constructor() : PrimeNumberAndGmailValidator<PrimeNumberAndGmailChild>(PrimeNumberAndGmailChild::class)

/***************************************************************************************************
 * Other Classes
 **************************************************************************************************/

internal object ValidatorsRunInfo {

    private val timesRunMap = ConcurrentHashMap<ValidatorId<*, *>, AtomicInteger>()

    fun incrementTimesRunFor(validatorId: ValidatorId<*, *>) {
        timesRunMap.getOrDefault(validatorId).incrementAndGet()
    }

    fun clear() = timesRunMap.clear()

    fun timesRun(validatorId: ValidatorId<*, *>): Int {
        return timesRunMap.getOrDefault(validatorId).get();
    }

    private inline fun ConcurrentHashMap<ValidatorId<*, *>, AtomicInteger>.getOrDefault(validatorId: ValidatorId<*, *>) = getOrPut(validatorId, { AtomicInteger() })
}

internal class ValidatorRunInfo(val validatorId: ValidatorId<*, *>) {

    fun incrementTimesRun() = ValidatorsRunInfo.incrementTimesRunFor(validatorId)
}

internal abstract class ConstraintValidatorWithRunInfo<A : Annotation, T> : ConstraintValidator<A, T> {

    private lateinit var validatorRunInfo: ValidatorRunInfo

    fun setupRunInfo(validatorId: ValidatorId<A, *>) {
        validatorRunInfo = ValidatorRunInfo(validatorId)
    }

    final override fun isValid(value: T, context: ConstraintValidatorContext): Boolean {
        validatorRunInfo.incrementTimesRun()
        return doIsValid(value, context)
    }

    abstract fun doIsValid(value: T, context: ConstraintValidatorContext): Boolean
}

internal abstract class PersistableObjectValidatorWithRunInfo<E : Entity, X : PersistableObjectValidationException>(
        val validatorId: ValidatorId<*, E>, createException: (String) -> X, vararg validatedFields: Path<*>
) : PersistableObjectValidator<E, X>(createException, *validatedFields) {

    private val validatorRunInfo: ValidatorRunInfo = ValidatorRunInfo(validatorId)

    final override fun E.isValid(): Boolean {
        validatorRunInfo.incrementTimesRun()
        return doIsValid()
    }

    abstract fun E.doIsValid(): Boolean
}

internal data class ValidatorId<V, C>(val validatorType: Class<V>, val validatedClass: Class<C>)