package com.rodrigodev.common.properties.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 14/10/16.
 */
class UnsupportedOperationDelegate<T>() : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        throw UnsupportedOperationException("Unsupported operation.")
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        throw UnsupportedOperationException("Unsupported operation.")
    }
}