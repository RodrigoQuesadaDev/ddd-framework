package com.rodrigodev.common.concurrent

/**
* Created by Rodrigo Quesada on 29/10/15.
*/
@Suppress("NOTHING_TO_INLINE")
inline fun Double.secsToMillis(): Long = (this * 1000).toLong()