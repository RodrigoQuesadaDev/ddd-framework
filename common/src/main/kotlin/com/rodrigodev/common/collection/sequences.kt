@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.collection

import java.util.*

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
inline fun <reified T> Sequence<T>.toTypedArray(): Array<T> = toArrayList().toTypedArray()

inline fun <T : Any> Sequence<T>.toTypedArray(array: Array<T?>): Array<T> = toArrayList().toArray(array)

inline fun <T> Sequence<T>.toArrayList(): ArrayList<T> = ArrayList<T>().apply { addAll(this@toArrayList) }

inline fun <T> Sequence<T>.peek(crossinline call: (T) -> Unit): Sequence<T> = map { it.apply { call(it) } }