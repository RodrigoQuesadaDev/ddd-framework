@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.preconditions

/**
 * Created by Rodrigo Quesada on 21/12/15.
 */
inline fun checkArgument(body: () -> Boolean, message: String = "") {
    if (!body()) {
        if (message.isEmpty()) throw IllegalArgumentException()
        else throw IllegalArgumentException(message)
    }
}