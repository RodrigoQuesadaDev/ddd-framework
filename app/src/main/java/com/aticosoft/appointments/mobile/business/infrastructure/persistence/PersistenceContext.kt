package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityCallbackListener
import com.querydsl.jdo.JDOQueryFactory
import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
import com.rodrigodev.common.properties.delegates.ThreadLocalDelegate
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

    private val threadLocalCleaner = ThreadLocalCleaner()

    val persistenceManager: PersistenceManager by ThreadLocalDelegate(threadLocalCleaner) { pmf.persistenceManager }

    val queryFactory: JDOQueryFactory by ThreadLocalDelegate(threadLocalCleaner) { JDOQueryFactory { persistenceManager } }

    private val entityListenerListBuilder = EntityListenerListBuilder()

    private val entityListeners: List<EntityCallbackListener<*>> by lazy { entityListenerListBuilder.build() }

    fun registerEntityListener(entityListener: EntityCallbackListener<*>) {
        pmf.addInstanceLifecycleListener(entityListener, arrayOf(entityListener.entityType.java))
        entityListenerListBuilder.add(entityListener)
    }

    fun onTransactionCommitted() {
        entityListeners.forEach { it.onTransactionCommitted() }
    }

    fun close() {
        threadLocalCleaner.cleanUpThreadLocalInstances()
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

private class EntityListenerListBuilder {

    private val listeners: MutableList<EntityCallbackListener<*>> = arrayListOf()

    fun add(listener: EntityCallbackListener<*>) {
        listeners.add(listener)
    }

    fun build() = listeners
}