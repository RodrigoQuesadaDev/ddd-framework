@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Singleton
internal class TestDataServices @Inject constructor(private val c: TestDataServices.Context) : ApplicationServices(c.superContext) {

    /***********************************************************************************************
     * Add entities
     **********************************************************************************************/

    class AddOddValueAndEmailParent(val oddValue: Int, val email: String, val childOddValue: Int, val childEmail: String) : Command()

    fun execute(command: AddOddValueAndEmailParent) = command.execute {
        c.oddValueAndEmailParentRepository.add(c.oddValueAndEmailParentFactory.create(oddValue, email, childOddValue, childEmail))
    }

    class AddPrimeNumberAndGmailParent(val primeNumber: Int, val email: String, val extraValue: Int, val childPrimeValue: Int, val childEmail: String, val childExtraValue: Int) : Command()

    fun execute(command: AddPrimeNumberAndGmailParent) = command.execute {
        c.primeNumberAndGmailParentRepository.add(c.primeNumberAndGmailParentFactory.create(primeNumber, email, extraValue, childPrimeValue, childEmail, childExtraValue))
    }

    /***********************************************************************************************
     * Update OddValueAndEmail entities
     **********************************************************************************************/

    class UpdateOddValueAndEmailParent(number: Int, email: String) : UpdateNumberAndEmail(number, email)

    fun execute(command: UpdateOddValueAndEmailParent) = command.updateParent { firstOddValueAndEmailParent() }

    class UpdateOddValueAndEmailChild(number: Int, email: String) : UpdateNumberAndEmail(number, email)

    fun execute(command: UpdateOddValueAndEmailChild) = command.updateChild { firstOddValueAndEmailParent() }

    private inline fun firstOddValueAndEmailParent(): OddValueAndEmailParent = c.oddValueAndEmailParentRepository.find(c.oddValueAndEmailParentQueries.first())!!

    /***********************************************************************************************
     * Update PrimeNumberAndGmail entities
     **********************************************************************************************/

    class UpdatePrimeNumberAndGmailParent(number: Int, email: String) : UpdateNumberAndEmail(number, email)

    fun execute(command: UpdatePrimeNumberAndGmailParent) = command.updateParent { firstPrimeNumberAndGmailParent() }

    class UpdatePrimeNumberAndGmailChild(number: Int, email: String) : UpdateNumberAndEmail(number, email)

    fun execute(command: UpdatePrimeNumberAndGmailChild) = command.updateChild { firstPrimeNumberAndGmailParent() }

    class UpdateExtraValueForPrimeNumberAndGmailParent(extraValue: Int) : UpdateExtraValue(extraValue)

    fun execute(command: UpdateExtraValueForPrimeNumberAndGmailParent) = command.updateParent { firstPrimeNumberAndGmailParent() }

    class UpdateExtraValueForPrimeNumberAndGmailChild(extraValue: Int) : UpdateExtraValue(extraValue)

    fun execute(command: UpdateExtraValueForPrimeNumberAndGmailChild) = command.updateChild { firstPrimeNumberAndGmailParent() }

    private inline fun firstPrimeNumberAndGmailParent(): PrimeNumberAndGmailParent = c.primeNumberAndGmailParentRepository.find(c.primeNumberAndGmailParentQueries.first())!!

    /***************************************************************************************************
     * Update Entities Code
     **************************************************************************************************/

    abstract class UpdateNumberAndEmail(val number: Int, val email: String) : ApplicationServices.Command()

    private fun UpdateNumberAndEmail.updateParent(firstEntityCall: () -> NumberAndEmailTestData) = updateUsing(firstEntityCall) {
        it.number = number
        it.email = email
    }

    private fun UpdateNumberAndEmail.updateChild(firstEntityCall: () -> NumberAndEmailTestDataParent<*>) = updateUsing(firstEntityCall) {
        it.child.let {
            it.number = number
            it.email = email
        }
    }

    private inline fun <T : NumberAndEmailTestData> UpdateNumberAndEmail.updateUsing(crossinline firstEntityCall: () -> T, crossinline updateCall: (T) -> Unit) = execute {
        updateCall(firstEntityCall())
    }

    abstract class UpdateExtraValue(val extraValue: Int) : ApplicationServices.Command()

    private fun UpdateExtraValue.updateParent(firstEntityCall: () -> PrimeNumberAndGmailValidatedTestData) = updateUsing(firstEntityCall) {
        it.extraValue = extraValue
    }

    private fun <C : PrimeNumberAndGmailValidatedTestData> UpdateExtraValue.updateChild(firstEntityCall: () -> NumberAndEmailTestDataParent<C>) = updateUsing(firstEntityCall) {
        it.child.let {
            it.extraValue = extraValue
        }
    }

    private inline fun <T : NumberAndEmailTestData> UpdateExtraValue.updateUsing(crossinline firstEntityCall: () -> T, crossinline updateCall: (T) -> Unit) = execute {
        updateCall(firstEntityCall())
    }

    @Singleton
    class Context @Inject protected constructor(
            val superContext: ApplicationServices.Context,
            val oddValueAndEmailParentFactory: OddValueAndEmailParentFactory,
            val oddValueAndEmailParentRepository: OddValueAndEmailParentRepository,
            val oddValueAndEmailParentQueries: OddValueAndEmailParentQueries,
            val primeNumberAndGmailParentFactory: PrimeNumberAndGmailParentFactory,
            val primeNumberAndGmailParentRepository: PrimeNumberAndGmailParentRepository,
            val primeNumberAndGmailParentQueries: PrimeNumberAndGmailParentQueries
    )
}