package com.aticosoft.appointments.mobile.business.infrastructure.persistence

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

    fun close() {
        persistenceManagerTL.remove()
        queryFactoryTL.remove()
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