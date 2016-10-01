package com.rodrigodev.common.properties.delegates

import java.util.concurrent.atomic.AtomicInteger
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 01/09/16.
 */
class AtomicIntegerDelegate(private val atomicValue: AtomicInteger = AtomicInteger()) : ReadWriteProperty<Any, Int> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = atomicValue.get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) = atomicValue.set(value)
}