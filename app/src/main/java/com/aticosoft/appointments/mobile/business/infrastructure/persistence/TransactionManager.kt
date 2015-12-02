@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.StaleEntityException
import org.datanucleus.PropertyNames
import org.datanucleus.javax.transaction.Status
import org.datanucleus.javax.transaction.Synchronization
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.JDOOptimisticVerificationException
import javax.jdo.PersistenceManager

/**
 * Created by Rodrigo Quesada on 23/09/15.
 */
@Singleton
/*internal*/ class TransactionManager @Inject constructor(
        val context: PersistenceContext
) {

    val transactionListener = object : Synchronization {

        override fun beforeCompletion() {
            // Do nothing!
        }

        override fun afterCompletion(status: Int) {
            if (status == Status.STATUS_COMMITTED) {
                context.onTransactionCommitted()
            }
        }
    }

    inline fun <R> transactional(call: () -> R): R = context.useThenClose {
        val result: R
        val pm = context.persistenceManager
        pm.doNotReadFromCache()
        val tx = pm.currentTransaction()
        tx.synchronization = transactionListener
        try {
            tx.begin()

            result = call()

            tx.commit()
        }
        catch(e: JDOOptimisticVerificationException) {
            throw StaleEntityException(e)
        }
        finally {
            if (tx.isActive) {
                tx.rollback()
            }
            pm.close()
        }
        result
    }

    fun PersistenceManager.doNotReadFromCache() = setProperty(PropertyNames.PROPERTY_CACHE_L2_RETRIEVE_MODE, "bypass")
}