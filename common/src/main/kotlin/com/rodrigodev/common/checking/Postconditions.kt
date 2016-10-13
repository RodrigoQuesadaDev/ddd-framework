@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.checking

/**
 * Created by Rodrigo Quesada on 12/10/16.
 */
object Postconditions {

    inline fun postcondition(value: Boolean) = postcondition(value, { "Failed post-condition." })

    inline fun postcondition(value: Boolean, lazyMessage: () -> Any) {
        if (!value) throw PostConditionException(lazyMessage().toString())
    }
}

class PostConditionException(message: String) : RuntimeException(message)