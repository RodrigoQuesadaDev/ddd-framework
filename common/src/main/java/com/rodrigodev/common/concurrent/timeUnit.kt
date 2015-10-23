package com.rodrigodev.common.concurrent

/**
 * Created by rodrigo on 29/10/15.
 */
@Suppress("NOTHING_TO_INLINE")
inline fun Double.secsToMillis(): Long = (this * 1000).toLong()