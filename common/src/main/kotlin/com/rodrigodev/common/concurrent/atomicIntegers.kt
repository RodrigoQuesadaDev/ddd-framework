@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.concurrent

import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by Rodrigo Quesada on 09/10/16.
 */
inline fun AtomicInteger.update(body: () -> Unit): Unit = synchronized(this) { body() }

inline infix operator fun AtomicInteger.compareTo(value: Int): Int = this.get().compareTo(value)

inline operator fun AtomicInteger.dec(): AtomicInteger = apply { decrementAndGet() }

inline operator fun AtomicInteger.inc(): AtomicInteger = apply { incrementAndGet() }