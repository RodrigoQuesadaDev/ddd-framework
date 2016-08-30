@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.reflection

import java.lang.reflect.ParameterizedType

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
private val collectionClasses = arrayOf(List::class, Set::class, Map::class)

fun ParameterizedType.isCollection() = collectionClasses.anyIsSuperOfOrSameAsAny(classes())

inline fun ParameterizedType.isParameterizedWith(aClass: Class<*>) = actualTypeArguments.any { it.isSubOfOrSameAs(aClass) }