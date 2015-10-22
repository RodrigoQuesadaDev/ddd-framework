package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityCallbackListener
import com.querydsl.jdo.JDOQueryFactory
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.PersistenceManager
import javax.jdo.PersistenceManagerFactory

/**
 * Created by rodrigo on 23/09/15.
 */
@Singleton
/*internal*/ class PersistenceContext @Inject constructor(
        private val pmf: PersistenceManagerFactory
) {

    private val persistenceManagerTL = object : ThreadLocal<PersistenceManager>() {
        override fun initialValue() = pmf.persistenceManager
    }

    val persistenceManager: PersistenceManager
        get() = persistenceManagerTL.get()

    private val queryFactoryTL = object : ThreadLocal<JDOQueryFactory>() {
        override fun initialValue() = JDOQueryFactory { persistenceManager }
    }

    val queryFactory: JDOQueryFactory
        get() = queryFactoryTL.get()

    private lateinit var entityListeners: Array<out EntityCallbackListener<*>>

    fun registerEntityListeners(vararg entityListeners: EntityCallbackListener<*>) {
        entityListeners.forEach { it.register(pmf) }
        this.entityListeners = entityListeners
    }

    fun onTransactionCommitted() {
        entityListeners.forEach { it.onTransactionCommitted() }
    }

    fun close() {
        persistenceManagerTL.remove()
        queryFactoryTL.remove()
        entityListeners.forEach { it.resetLocalState() }
    }

    inline fun <R> useThenClose(call: () -> R): R {
        return try {
            call()
        }
        finally {
            close()
        }
    }
}