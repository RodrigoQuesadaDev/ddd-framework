@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.collection

import kotlin.collections.plus

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
@Suppress("UNCHECKED_CAST")
inline operator fun <T> Array<out T>.plus(array: Array<out T>): Array<out T> = (this as Array<T>) + array

inline fun arrayOfZeros(size: Int): Array<Int> = Array(size, { 0 })

@Suppress("UNCHECKED_CAST")
inline fun <T> Array<T>.nullCopy(): Array<T?> = java.lang.reflect.Array.newInstance(javaClass.componentType, this.size) as Array<T?>