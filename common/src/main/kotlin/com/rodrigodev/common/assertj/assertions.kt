@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.assertj

import org.assertj.core.api.AbstractListAssert
import org.assertj.core.api.Assertions.assertThat

/**
 * Created by Rodrigo Quesada on 18/10/16.
 */
object Assertions {

    fun <T> assertThatList(list: List<T>) = CustomListAssert(list)
}

class CustomListAssert<T>(actual: List<T>)
: AbstractListAssert<CustomListAssert<T>, List<T>, T>(actual, CustomListAssert::class.java) {

    fun containsExactlyElementsOfInAnyOrder(list: List<T>): CustomListAssert<T> {

        val otherElementsGrouped = list.groupBy { it }
        val elementsIndexMap = otherElementsGrouped.associateKeysToIndex()

        val elementsSorted = actual.sortUsing(elementsIndexMap)
        val otherElementsSorted = otherElementsGrouped.flatMap { it.value }

        assertThat(elementsSorted).containsExactlyElementsOf(otherElementsSorted)
        return this
    }

    private inline fun Map<T, List<T>>.associateKeysToIndex() = asSequence()
            .mapIndexed { i, entry -> IndexedElement(entry.key, i) }
            .associateBy({ it.element }, { it.index })

    private inline fun <E> List<E>.sortUsing(elementsIndexMap: Map<E, Int>) = sortedBy { elementsIndexMap[it] ?: -1 }
}

//region Other Classes
private data class IndexedElement<out T>(val element: T, val index: Int)
//endregion