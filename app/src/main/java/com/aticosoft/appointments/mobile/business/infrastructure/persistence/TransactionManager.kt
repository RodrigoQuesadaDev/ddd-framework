package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 23/09/15.
 */
@Singleton
/*internal*/ class TransactionManager @Inject constructor(
        val context: PersistenceContext
) {

    inline fun <R> transactional(call: () -> R): R = context.useThenClose {
        val result: R
        val pm = context.persistenceManager
        val tx = pm.currentTransaction()
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