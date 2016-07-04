@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.collection

/**
 * Created by Rodrigo Quesada on 12/11/15.
 */
inline fun <E> List<E>.subList(range: IntRange): List<E> = subList(range.start, range.endInclusive)