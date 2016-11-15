@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.properties

import com.rodrigodev.common.properties.delegates.*
import com.rodrigodev.common.properties.delegates.PostInitializedPropertyDelegate.PropertyInitializer
import com.rodrigodev.common.properties.delegates.UnsafePostInitializedPropertyDelegate.UnsafePropertyInitializer
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Rodrigo Quesada on 02/10/16.
 */
object Delegates {

    inline fun atomicBoolean(atomicValue: AtomicBoolean = AtomicBoolean()) = AtomicBooleanDelegate(atomicValue)

    inline fun atomicInteger(atomicValue: AtomicInteger = AtomicInteger()) = AtomicIntegerDelegate(atomicValue)

    inline fun <T> threadLocal(cleaner: ThreadLocalCleaner, noinline initialValueCall: () -> T)
            = ThreadLocalDelegate(cleaner, initialValueCall)

    inline fun <T> writableLazy(noinline initializer: () -> T) = WritableLazy(initializer)

    inline fun <T> unsafeLazy(noinline initializer: () -> T) = lazy(LazyThreadSafetyMode.NONE, initializer)

    inline fun <T> readOnly(initialValue: T) = ReadOnlyDelegate<T>(initialValue)

    inline fun <T : Any> writeOnce() = WriteOnceDelegate<T>()

    inline fun <T : Any> writeOnce(initialValue: T) = WriteOnceDelegate(initialValue)

    inline fun <T : Any> lateinitReadOnly() = writeOnce<T>()

    inline fun <T> unsupportedOperation() = UnsupportedOperationDelegate<T>()

    inline fun <T : Any> postInitialized(
            type: PostInitializedPropertyType,
            initializer: PropertyInitializer,
            noinline initialization: () -> T
    ): PostInitializedPropertyDelegate<T> = when (type) {
        PostInitializedPropertyType.UNSAFE -> {
            require(initializer is UnsafePropertyInitializer, { "Initializer should be of type UnsafePropertyInitializer." })
            UnsafePostInitializedPropertyDelegate(initializer as UnsafePropertyInitializer, initialization)
        }
    }

    inline fun <T : Any> PostInitialized.postInitialized(noinline initialization: () -> T)
            : PostInitializedPropertyDelegate<T> = Delegates.postInitialized(_postInitializedPropertyType, _propertyInitializer, initialization)
}

//region Other Classes
//TODO implement safe version based on lazy
enum class PostInitializedPropertyType { UNSAFE }

interface PostInitialized {

    val _propertyInitializer: PropertyInitializer
    val _postInitializedPropertyType: PostInitializedPropertyType
}

interface UnsafePostInitialized : PostInitialized {

    override val _propertyInitializer: UnsafePropertyInitializer
    override val _postInitializedPropertyType: PostInitializedPropertyType
        get() = PostInitializedPropertyType.UNSAFE

    fun _postInit() {
        _propertyInitializer.init()
    }
}
//endregion
