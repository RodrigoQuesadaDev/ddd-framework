@file:Suppress("NOTHING_TO_INLINE", "NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")

package com.rodrigodev.common.bitwise

/**
 * Created by Rodrigo Quesada on 31/10/16.
 */
object LongBitOperations {

    inline fun Long.setBit(index: Int): Long = or(SET_BIT_MASKS[index])

    inline fun Long.clearBit(index: Int): Long = and(CLEAR_BIT_MASKS[index])

    inline fun Long.clear(): Long = 0

    inline fun Long.setAllBits(): Long = ZERO_INVERTED

    inline fun Long.isEmpty(): Boolean = this == 0L

    inline fun Long.isFull(): Boolean = this == ZERO_INVERTED
}

internal const val ZERO_INVERTED: Long = 0.inv()

internal val SET_BIT_MASKS: Array<Long> = (0..63).map { 1L.shl(it) }.toTypedArray()

internal val CLEAR_BIT_MASKS: Array<Long> = SET_BIT_MASKS.map(Long::inv).toTypedArray()