@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.reflection

/**
 * Created by Rodrigo Quesada on 30/06/16.
 */
inline fun Class<*>.isAssignableFromAny(types: Iterable<Class<*>>) = types.any { isAssignableFrom(it) }