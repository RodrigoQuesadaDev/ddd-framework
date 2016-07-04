package com.rodrigodev.common.properties.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 04/07/16.
 */
internal class WritableLazy<T>(val initializer: () -> T) : ReadWriteProperty<Any, T> {

    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        if (value == null) {
            value = initializer()
        }
        return value!!
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
    }
}