package com.rodrigodev.common.collection

/**
 * Created by Rodrigo Quesada on 23/11/15.
 */
@Suppress("UNCHECKED_CAST")
public operator fun <T> Array<out T>.plus(array: Array<out T>): Array<out T> = (this as Array<T>) + array