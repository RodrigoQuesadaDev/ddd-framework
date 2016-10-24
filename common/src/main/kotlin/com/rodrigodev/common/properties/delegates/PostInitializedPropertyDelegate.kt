@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.properties.delegates

import com.rodrigodev.common.collection.unmodifiable
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 26/10/15.
 */
private val DUMMY_REGISTERED_DELEGATES = mutableListOf<PostInitializedPropertyDelegate<*>>().unmodifiable()

abstract class PostInitializedPropertyDelegate<T : Any> : ReadOnlyProperty<Any, T> {

    protected abstract var value: T?
    protected abstract val initialization: () -> T

    protected fun setUp(initializer: PropertyInitializer) {
        initializer.register(this)
    }

    private inline fun initValue() {
        check(value == null, { "The property has already been initialized." })
        value = initialization()
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        check(value != null, { "The property has not been initialized yet." })
        return value!!
    }

    abstract class PropertyInitializer {
        companion object {
            val DUMMY = object : PropertyInitializer() {
                override val registeredDelegates = DUMMY_REGISTERED_DELEGATES
            }
        }

        protected abstract val registeredDelegates: MutableList<PostInitializedPropertyDelegate<*>>

        fun register(delegate: PostInitializedPropertyDelegate<*>) {
            registeredDelegates.add(delegate)
        }

        open fun init() {
            registeredDelegates.forEach { it.initValue() }
        }
    }
}

class UnsafePostInitializedPropertyDelegate<T : Any>(initializer: UnsafePropertyInitializer, override val initialization: () -> T) : PostInitializedPropertyDelegate<T>() {

    override var value: T? = null

    init {
        setUp(initializer)
    }

    open class UnsafePropertyInitializer : PropertyInitializer() {
        companion object {
            val DUMMY = object : UnsafePropertyInitializer() {
                override val registeredDelegates = DUMMY_REGISTERED_DELEGATES
            }
        }

        override val registeredDelegates: MutableList<PostInitializedPropertyDelegate<*>> = arrayListOf()
    }
}