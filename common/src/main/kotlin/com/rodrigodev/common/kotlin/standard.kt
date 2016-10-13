package com.rodrigodev.common.kotlin

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */

/**
 * Calls the specified function [block] with `this` value as its argument and returns `this` value.
 */
inline fun <T, R> T.pass(block: (T) -> R): T {
    block(this); return this
}

inline fun <T, R> T.nullable(block: T.() -> R): R? = try {
    block()
}
catch(e: Throwable) {
    null
}