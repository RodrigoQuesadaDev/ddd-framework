@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.infrastructure.persistence

//TODO uncomment to run DN on Android
/*import org.datanucleus.javax.transaction.Status
import org.datanucleus.javax.transaction.Synchronization*/
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.StalePersistableObjectException
import org.datanucleus.PropertyNames
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.JDOOptimisticVerificationException
import javax.jdo.PersistenceManager
import javax.transaction.Status
import javax.transaction.Synchronization

/**
 * Created by Rodrigo Quesada on 23/09/15.
 */
@Singleton
/*internal*/ class TransactionManager @Inject protected constructor() {

    lateinit var context: PersistenceContext

    fun init(context: PersistenceContext) {
        this.context = context
    }

    val transactionListener = object : Synchronization {

        override fun beforeCompletion() {
            // Do nothing!
        }

        override fun afterCompletion(status: Int) {
            if (status == Status.STATUS_COMMITTED) {
                context.onTransactionCommitted()
                //TODO remove when this issue is fixed in the future?
                //Issue: this prevents the listener from being called again for the same transaction
                context.persistenceManager.currentTransaction().synchronization = null
            }
        }
    }

    inline fun <R> withinTransactional(call: () -> R): R {
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
            throw StalePersistableObjectException(e)
        }
        finally {
            if (tx.isActive) {
                tx.rollback()
            }
        }
        return result
    }

    fun PersistenceManager.doNotReadFromCache() = setProperty(PropertyNames.PROPERTY_CACHE_L2_RETRIEVE_MODE, "bypass")
}