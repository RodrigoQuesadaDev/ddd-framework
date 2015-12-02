package com.rodrigodev.common.collection

/**
 * Created by Rodrigo Quesada on 12/11/15.
 */
fun <E> List<E>.subList(range: IntRange): List<E> = subList(range.start, range.endInclusive)