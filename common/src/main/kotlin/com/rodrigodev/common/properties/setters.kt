@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.properties

/**
 * Created by Rodrigo Quesada on 21/08/16.
 */
inline fun preventSetterCall() {
    throw UnsupportedOperationException("Setter not supported.")
}