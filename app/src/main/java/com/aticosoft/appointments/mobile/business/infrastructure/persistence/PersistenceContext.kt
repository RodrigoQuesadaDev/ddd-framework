package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.PersistableObjectLifecycleListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListener
import com.querydsl.jdo.JDOQueryFactory
import com.rodrigodev.common.properties.Delegates.threadLocal
import com.rodrigodev.common.properties.Delegates.unsafeLazy
import com.rodrigodev.common.properties.delegates.ThreadLocalCleaner
import org.datanucleus.PropertyNames
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

    private val persistableObjectAsyncListenerListBuilder = PersistableObjectAsyncListenerListBuilder()

    // It is fine to use unsafeLazy here, thread safety necessary here
    private val persistableObjectAsyncListeners: List<PersistableObjectAsyncListener<*>> by unsafeLazy { persistableObjectAsyncListenerListBuilder.build() }

    fun registerPersistableObjectAsyncListener(listener: PersistableObjectAsyncListener<*>) {
        registerPersistableObjectLifecycleListener(listener)
        persistableObjectAsyncListenerListBuilder.add(listener)
    }

    fun registerPersistableObjectLifecycleListener(persistableObjectLifecycleListener: PersistableObjectLifecycleListener<*>) {
        registerLifecycleListener(persistableObjectLifecycleListener, persistableObjectLifecycleListener.objectType)
    }

    fun registerLifecycleListener(lifecycleListener: InstanceLifecycleListener, vararg instanceTypes: Class<*>) {
        pmf.addInstanceLifecycleListener(lifecycleListener, instanceTypes)
    }

    fun onTransactionCommitted() {
        persistableObjectAsyncListeners.forEach { it.onTransactionCommitted() }
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <R> execute(transactional: Boolean = true, block: () -> R): R = if (transactional) {
        executeWithinContext { tm.withinTransactional { block() } }
    }
    else executeWithinContext {
        // Allow non-transactional reads
        persistenceManager.setProperty(PropertyNames.PROPERTY_NONTX_READ, true)
        block()
    }

    internal inline fun <R> executeWithinContext(block: () -> R): R = use { block() }

    override fun close() {
        persistenceManager.close()
        threadLocalCleaner.cleanUpThreadLocalInstances()
        persistableObjectAsyncListeners.forEach { it.resetLocalState() }
    }

    interface PersistenceManagerFactoryAccessor {
        val PersistenceContext.pmf: PersistenceManagerFactory
            get() = this.pmf
    }
}

private class PersistableObjectAsyncListenerListBuilder {

    private val listeners: MutableList<PersistableObjectAsyncListener<*>> = arrayListOf()

    fun add(listener: PersistableObjectAsyncListener<*>) {
        listeners.add(listener)
    }

    fun build(): List<PersistableObjectAsyncListener<*>> = listeners
}