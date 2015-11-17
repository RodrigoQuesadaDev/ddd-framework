package com.rodrigodev.common.collection

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
inline fun <reified T> Sequence<T>.toTypedArray(): Array<T> = toArrayList().toTypedArray()