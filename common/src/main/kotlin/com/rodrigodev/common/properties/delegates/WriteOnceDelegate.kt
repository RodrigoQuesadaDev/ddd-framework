package com.rodrigodev.common.properties.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 02/10/16.
 */
class WriteOnceDelegate<T : Any>() : ReadWriteProperty<Any, T> {

    private var value: T? = null

    constructor(initialValue: T) : this() {
        value = initialValue
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        check(value != null, { "The value of the property \"${property.name}\" hasn't being set." })
        return value!!
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        check(this.value == null, { "The value of the property \"${property.name}\" can only be set once." })
        this.value = value
    }
}