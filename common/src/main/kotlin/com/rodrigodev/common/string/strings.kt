@file:Suppress("NOTHING_TO_INLINE", "NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")

package com.rodrigodev.common.string

import org.apache.commons.lang3.StringUtils

/**
 * Created by Rodrigo Quesada on 12/11/15.
 */
fun String.count(substring: String) = StringUtils.countMatches(this, substring)

fun String.remove(value: Char) = replace(value.toString(), "")

fun String.remove(regex: Regex) = replace(regex, "")

internal val UNSIGNED_HEX_LONG_REGEX = Regex("^0x|_")

internal val UNSIGNED_BIN_LONG_REGEX = Regex("^0b|_")

// Java 1.8 and up
inline fun String.parseUnsignedLong(radix: Int, removeRegex: Regex) = java.lang.Long.parseUnsignedLong(this.remove(removeRegex), radix)

// Java 1.8 and up
inline fun String.parseUnsignedHexLong() = parseUnsignedLong(16, UNSIGNED_HEX_LONG_REGEX)

// Java 1.8 and up
inline fun String.parseUnsignedBinaryLong() = parseUnsignedLong(2, UNSIGNED_BIN_LONG_REGEX)