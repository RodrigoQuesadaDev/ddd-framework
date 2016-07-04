@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.concurrent

/**
 * Created by Rodrigo Quesada on 29/10/15.
 */
inline fun Double.secsToMillis(): Long = (this * 1000).toLong()

inline fun Double.minsToMillis(): Long = (this * 60).secsToMillis()