package com.rodrigodev.common.spec.story.transformer

import com.rodrigodev.common.collection.subList
import com.rodrigodev.common.string.count
import com.rodrigodev.common.testing.number.isDivisibleBy
import org.jbehave.core.model.TableTransformers.TableTransformer
import java.util.*

/**
 * Created by Rodrigo Quesada on 12/11/15.
 */
internal class MultilineTableTransformer : TableTransformer {
    companion object {
        val NAME = "MULTILINE"
        private val ROW_SEPARATOR = "\n"
    }

    override fun transform(tableAsString: String, properties: Properties): String = with(tableAsString.lines()) {
        headers() + ROW_SEPARATOR + body(properties)
    }

    private fun List<String>.headers() = first()

    private fun List<String>.body(properties: Properties) = calculateRowIndexes(properties).plus(size)
            .toRanges()
            .toRows(this)
            .joinToString(ROW_SEPARATOR)

    private fun List<String>.calculateRowIndexes(properties: Properties): Sequence<Int> = SimpleSequence {
        val separatorCount = first().count(properties.headerSeparator())
        val valueSeparator = properties.valueSeparator()
        var currentCount = 0

        (1..lastIndex).filter {
            val isRowIndex = currentCount.isDivisibleBy(separatorCount)
            currentCount += this[it].count(valueSeparator)
            isRowIndex
        }.iterator()
    }

    private fun Sequence<Int>.toRanges(): Sequence<IntRange> = SimpleSequence {
        // Force processing of sequence here in order to avoid doing it twice next (zip and drop)
        val intList = this.toList().asSequence()
        intList.zip(intList.drop(1)).map { IntRange(it.first, it.second) }.iterator()
    }

    private fun Sequence<IntRange>.toRows(lines: List<String>) = map { range ->
        lines.subList(range).joinToString(separator = "")
    }
}

private fun Properties.ignorableSeparator() = getProperty("ignorableSeparator", "|--")
private fun Properties.headerSeparator() = getProperty("headerSeparator", "|")
private fun Properties.valueSeparator() = getProperty("valueSeparator", "|")

private class SimpleSequence<T>(val createIterator: () -> Iterator<T>) : Sequence<T> {
    override fun iterator(): Iterator<T> = createIterator()
}