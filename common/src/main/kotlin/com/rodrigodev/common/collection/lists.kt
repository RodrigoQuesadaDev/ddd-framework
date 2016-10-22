@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.collection

import com.rodrigodev.common.kotlin.pass
import java.util.*
import java.util.Collections.unmodifiableList

/**
 * Created by Rodrigo Quesada on 12/11/15.
 */
//region Constants
internal val UNMODIFIABLE_LIST_CLASS: Class<*> = unmodifiableList<String>(emptyList()).javaClass
//endregion

inline fun <E> List<E>.subList(range: IntRange): List<E> = subList(range.start, range.endInclusive)

inline fun <E> List<E>.shuffle(): List<E> = toMutableList().pass { Collections.shuffle(it) }

inline fun <E> MutableList<E>.synchronized(): MutableList<E> = Collections.synchronizedList(this)

@Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
inline fun <E> MutableList<E>.unmodifiable(): MutableList<E> = if (this !is UnmodifiableList<*>) UnmodifiableList(this) else this

internal class UnmodifiableList<E>(list: List<E>) : MutableList<E> by unmodifiableList(list)