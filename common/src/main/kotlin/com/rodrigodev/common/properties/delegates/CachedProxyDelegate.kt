package com.rodrigodev.common.properties.delegates

import com.rodrigodev.common.properties.Delegates.writableLazy
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 03/07/16.
 */
open class CachedProxyDelegate<P, T>(
        private val proxiedProperty: ProxiedProperty<T>,
        private val proxier: Proxier<P, T>
) : ReadWriteProperty<Any, P> {

    private var cachedValue: P by writableLazy { with(proxier) { proxiedProperty.getValue().proxy() } }

    override fun getValue(thisRef: Any, property: KProperty<*>): P = cachedValue

    override fun setValue(thisRef: Any, property: KProperty<*>, value: P) = with(proxier) {
        cachedValue = value
        proxiedProperty.setValue(value.unProxy())
    }

    open class ProxiedProperty<T>(private val get: () -> T, private val set: (T) -> Unit) {
        fun getValue(): T = get()
        fun setValue(value: T): Unit = set(value)
    }

    open class Proxier<P, T>(private val proxyImpl: T.() -> P, private val unProxyImpl: P.() -> T) {
        fun T.proxy(): P = proxyImpl()
        fun P.unProxy(): T = unProxyImpl()
    }
}