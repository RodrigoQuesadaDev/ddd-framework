package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import org.datanucleus.javax.transaction.Status
import org.datanucleus.javax.transaction.Synchronization
import javax.inject.Inject
import javax.inject.Singleton

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
        val tx = pm.currentTransaction()
        tx.synchronization = transactionListener
        try {
            tx.begin()

            result = call()

            tx.commit()
        }
        finally {
            if (tx.isActive) {
                tx.rollback()
            }
            pm.close()
        }
        result
    }
}