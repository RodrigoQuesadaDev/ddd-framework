package com.rodrigodev.common.properties.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by rodrigo on 26/10/15.
 */
class ThreadLocalDelegate<T>(val cleaner: ThreadLocalCleaner, initialValueCall: () -> T) : ReadWriteProperty<Any, T> {

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

class ThreadLocalCleaner {

    private val registeredThreadLocals = arrayListOf<ThreadLocal<*>>()

    fun register(threadLocal: ThreadLocal<*>) {
        registeredThreadLocals.add(threadLocal)
    }

    fun cleanUpThreadLocalInstances() {
        registeredThreadLocals.forEach { it.remove() }
    }
}