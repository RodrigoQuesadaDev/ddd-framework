package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObjectLifecycleListener
import com.querydsl.jdo.JDOQueryFactory
import com.rodrigodev.common.properties.Delegates.threadLocal
import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
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
        private val pmf: PersistenceManagerFactory,
        val tm: TransactionManager
) : Closeable {

    init {
        tm.init(this)
    }

    private val threadLocalCleaner = ThreadLocalCleaner()

    val persistenceManager: PersistenceManager by threadLocal(threadLocalCleaner) { pmf.persistenceManager }

    val queryFactory: JDOQueryFactory by threadLocal(threadLocalCleaner) { JDOQueryFactory { persistenceManager } }

    private val persistableObjectListenerListBuilder = PersistableObjectListenerListBuilder()

    private val persistableObjectListeners: List<PersistableObjectListener<*, *>> by lazy { persistableObjectListenerListBuilder.build() }

    fun registerPersistableObjectListener(persistableObjectListener: PersistableObjectListener<*, *>) {
        registerPersistableObjectLifecycleListener(persistableObjectListener)
        persistableObjectListenerListBuilder.add(persistableObjectListener)
    }

    fun registerPersistableObjectLifecycleListener(persistableObjectLifecycleListener: PersistableObjectLifecycleListener<*>) {
        registerLifecycleListener(persistableObjectLifecycleListener, persistableObjectLifecycleListener.objectType)
    }

    fun registerLifecycleListener(lifecycleListener: InstanceLifecycleListener, vararg instanceTypes: Class<*>) {
        pmf.addInstanceLifecycleListener(lifecycleListener, instanceTypes)
    }

    fun onTransactionCommitted() {
        persistableObjectListeners.forEach { it.onTransactionCommitted() }
    }

    inline fun <R> execute(transactional: Boolean = true, block: () -> R): R = if (transactional) {
        executeWithinContext { tm.withinTransactional { block() } }
    }
    else executeWithinContext { block() }

    inline fun <R> executeWithinContext(block: () -> R): R = use { block() }

    override fun close() {
        persistenceManager.close()
        threadLocalCleaner.cleanUpThreadLocalInstances()
        persistableObjectListeners.forEach { it.resetLocalState() }
    }

    interface PersistenceManagerFactoryAccessor {
        val PersistenceContext.pmf: PersistenceManagerFactory
            get() = this.pmf
    }
}

private class PersistableObjectListenerListBuilder {

    private val listeners: MutableList<PersistableObjectListener<*, *>> = arrayListOf()

    fun add(listener: PersistableObjectListener<*, *>) {
        listeners.add(listener)
    }

    fun build() = listeners
}