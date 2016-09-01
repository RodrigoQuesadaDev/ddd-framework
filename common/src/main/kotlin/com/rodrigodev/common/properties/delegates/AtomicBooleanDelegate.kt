package com.rodrigodev.common.properties.delegates

import java.util.concurrent.atomic.AtomicBoolean
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 01/09/16.
 */
class AtomicBooleanDelegate(private val atomicBoolean: AtomicBoolean = AtomicBoolean()) : ReadWriteProperty<Any, Boolean> {

    override fun getValue(thisRef: Any, property: KProperty<*>) = atomicBoolean.get()

    override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) = atomicBoolean.set(value)
}