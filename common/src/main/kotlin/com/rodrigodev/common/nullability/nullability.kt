@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.nullability

/**
 * Created by Rodrigo Quesada on 03/07/16.
 */
inline fun <T : Any, R : Any> T?.nullOr(body: (T) -> R): R? = this?.let(body)

inline fun <T : Any> T?.nonNullOr(defaultValue: Boolean, body: T.() -> Boolean): Boolean = this?.run(body) ?: defaultValue