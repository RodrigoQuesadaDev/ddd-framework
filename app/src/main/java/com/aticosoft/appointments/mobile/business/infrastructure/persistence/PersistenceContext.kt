package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.querydsl.jdo.JDOQueryFactory
import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
import com.rodrigodev.common.properties.delegates.ThreadLocalDelegate
import java.io.Closeable
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.PersistenceManager
import javax.jdo.PersistenceManagerFactory
import javax.jdo.listener.InstanceLifecycleListener

/**
 * Created by Rodrigo Quesada on 23/09/15.
 */
@Singleton
/*internal*/ class PersistenceContext @Inject constructor(
        private val pmf: PersistenceManagerFactory
) : Closeable {

    private val threadLocalCleaner = ThreadLocalCleaner()

    val persistenceManager: PersistenceManager by ThreadLocalDelegate(threadLocalCleaner) { pmf.persistenceManager }

    val queryFactory: JDOQueryFactory by ThreadLocalDelegate(threadLocalCleaner) { JDOQueryFactory { persistenceManager } }

    private val entityListenerListBuilder = EntityListenerListBuilder()

    private val entityListeners: List<EntityListener<*>> by lazy { entityListenerListBuilder.build() }

    fun registerEntityListener(entityListener: EntityListener<*>) {
        registerLifecycleListener(entityListener, entityListener.entityType)
        entityListenerListBuilder.add(entityListener)
    }

    fun registerLifecycleListener(lifecycleListener: InstanceLifecycleListener, instanceType: Class<out Entity>) {
        pmf.addInstanceLifecycleListener(lifecycleListener, arrayOf(instanceType))
    }

    fun onTransactionCommitted() {
        entityListeners.forEach { it.onTransactionCommitted() }
    }

    override fun close() {
        threadLocalCleaner.cleanUpThreadLocalInstances()
        entityListeners.forEach { it.resetLocalState() }
    }

    interface PersistenceManagerFactoryAccessor {
        val PersistenceContext.pmf: PersistenceManagerFactory
            get() = this.pmf
    }
}

private class EntityListenerListBuilder {

    private val listeners: MutableList<EntityListener<*>> = arrayListOf()

    fun add(listener: EntityListener<*>) {
        listeners.add(listener)
    }

    fun build() = listeners
}