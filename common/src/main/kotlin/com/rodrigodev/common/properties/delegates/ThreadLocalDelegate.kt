@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.properties.delegates

import com.rodrigodev.common.collection.synchronized
import com.rodrigodev.common.collection.unmodifiable
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 26/10/15.
 */
class ThreadLocalDelegate<T>(cleaner: ThreadLocalCleaner, initialValueCall: () -> T) : ReadWriteProperty<Any, T> {

    private val localValue = object : ThreadLocal<T>() {
        override fun initialValue(): T {
            return initialValueCall()
        }
    }

    init {
        cleaner.register(localValue)
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T = localValue.get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = localValue.set(value)
}


open class ThreadLocalCleaner {

    protected open var registeredThreadLocals: MutableList<ThreadLocal<*>> = arrayListOf()

    fun register(threadLocal: ThreadLocal<*>) {
        registeredThreadLocals.add(threadLocal)
    }

    open fun cleanUpThreadLocalInstances() {
        registeredThreadLocals.forEach { it.remove() }
    }
}

class SafeThreadLocalCleaner : ThreadLocalCleaner() {

    @Volatile override var registeredThreadLocals = arrayListOf<ThreadLocal<*>>().synchronized()
    @Volatile private var registrationsClosed = false

    override fun cleanUpThreadLocalInstances() {
        closeRegistrations()
        super.cleanUpThreadLocalInstances()
    }

    private inline fun closeRegistrations() {
        if (!registrationsClosed) {
            registeredThreadLocals = registeredThreadLocals.unmodifiable()
            registrationsClosed = true
        }
    }
}