package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.google.auto.factory.Provided
import org.hibernate.validator.constraints.Email
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
internal interface NumberAndEmailTestData {
    var number: Int
    var email: String
}

internal interface NumberAndEmailTestDataParent<C : NumberAndEmailTestData> : NumberAndEmailTestData {
    var child: C
}

internal abstract class OddValueAndEmailValidatedTestData(context: Entity.Context, email: String) : Entity(context), NumberAndEmailTestData {

    @Email(message = "value '\${validatedValue}' is not a valid email") override var email = email
}

@PersistenceCapable
//@AutoFactory
internal class OddValueAndEmailParent protected constructor(
        @Provided c: Context, oddValue: Int, email: String, childOddValue: Int, childEmail: String
) : OddValueAndEmailValidatedTestData(c.entityContext, email), NumberAndEmailTestDataParent<OddValueAndEmailChild> {

    @OddValue(entityType = OddValueAndEmailParent::class) override var number = oddValue
    override var child = c.oddValueAndEmailChildFactory.create(childOddValue, childEmail)

    @Singleton
    class Context @Inject protected constructor(
            val entityContext: Entity.Context,
            val oddValueAndEmailChildFactory: OddValueAndEmailChildFactory
    )
}

@PersistenceCapable
//@AutoFactory
internal class OddValueAndEmailChild constructor(@Provided context: Entity.Context, oddValue: Int, email: String) : OddValueAndEmailValidatedTestData(context, email) {

    @OddValue(entityType = OddValueAndEmailChild::class) override var number = oddValue
}

internal abstract class PrimeNumberAndGmailValidatedTestData(context: Entity.Context, number: Int, email: String, extraValue: Int) : Entity(context), NumberAndEmailTestData {

    override var number = number
    @Email override var email = email
    var extraValue = extraValue
}

@PersistenceCapable
//@AutoFactory
internal class PrimeNumberAndGmailParent protected constructor(
        @Provided c: Context, primeNumber: Int, email: String, extraValue: Int, childPrimeValue: Int, childEmail: String, childExtraValue: Int
) : PrimeNumberAndGmailValidatedTestData(c.entityContext, primeNumber, email, extraValue), NumberAndEmailTestDataParent<PrimeNumberAndGmailChild> {

    override var child = c.primeNumberAndGmailChildFactory.create(childPrimeValue, childEmail, childExtraValue)

    @Singleton
    class Context @Inject protected constructor(
            val entityContext: Entity.Context,
            val primeNumberAndGmailChildFactory: PrimeNumberAndGmailChildFactory
    )
}

@PersistenceCapable
//@AutoFactory
internal class PrimeNumberAndGmailChild constructor(@Provided context: Entity.Context, primeNumber: Int, email: String, extraValue: Int) : PrimeNumberAndGmailValidatedTestData(context, primeNumber, email, extraValue)