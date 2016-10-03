package com.rodrigodev.common.properties.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 02/10/16.
 */
class ReadOnlyDelegate<T>(initialValue: T) : ReadWriteProperty<Any, T> {

    private var value: T = initialValue

    override fun getValue(thisRef: Any, property: KProperty<*>) = value

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        throw UnsupportedOperationException("The property \"${property.name}\" is read-only.")
    }
}