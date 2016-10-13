@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.collection

import com.rodrigodev.common.checking.Postconditions.postcondition
import com.rodrigodev.common.kotlin.pass
import java.util.*

/**
 * Created by Rodrigo Quesada on 12/10/16.
 */
inline fun <T, K> Collection<T>.identifiedBy(keySelector: (T) -> K): Map<K, T> {
    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    return identifiedByTo(
            mapWithOptimizedCapacity<T, K, MutableMap<K, T>>(this, ::LinkedHashMap),
            keySelector
    )
}

inline fun <T, K, M : MutableMap<in K, in T>> Collection<T>.identifiedByTo(destination: M, keySelector: (T) -> K)
        : M = associateByTo(destination, keySelector).pass { postcondition(it.size == size) }

inline fun <T, K : Comparable<K>> Collection<T>.identifiedBySorted(keySelector: (T) -> K)
        : SortedMap<K, T> = identifiedByTo(TreeMap(), keySelector)

//region Utils
private const val INT_MAX_POWER_OF_TWO: Int = Int.MAX_VALUE / 2 + 1

internal inline fun <T, K, M : MutableMap<in K, in T>> mapWithOptimizedCapacity(baseCollection: Collection<T>, createMap: (size: Int) -> M)
        : M = createMap(mapCapacity(baseCollection.size).coerceAtLeast(16))

/**
 * Calculate the initial capacity of a map, based on Guava's com.google.common.collect.Maps approach. This is equivalent
 * to the Collection constructor for HashSet, (c.size()/.75f) + 1, but provides further optimisations for very small or
 * very large sizes, allows support non-collection classes, and provides consistency for all map based class construction.
 */
internal inline fun mapCapacity(expectedSize: Int): Int {
    if (expectedSize < 3) {
        return expectedSize + 1
    }
    if (expectedSize < INT_MAX_POWER_OF_TWO) {
        return expectedSize + expectedSize / 3
    }
    return Int.MAX_VALUE // any large value
}
//endregion