@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.reflection

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable

/**
 * Created by Rodrigo Quesada on 30/06/16.
 */
fun Type.classes(): List<Class<*>> = when (this) {
    is Class<*> -> listOf(this)
    is ParameterizedType -> rawType.classes()
    is TypeVariable<*> -> this.bounds.flatMap { it.classes() }
    else -> emptyList()
}

inline fun Type.isSubOfOrSameAs(aClass: Class<*>): Boolean = classes().any { it.isSubOfOrSameAs(aClass) }

inline fun Type.isArrayOf(aClass: Class<*>): Boolean {
    return this is Class<*> && isArray() && this.componentType.isSubOfOrSameAs(aClass)
}

inline fun Type.isCollectionOf(aClass: Class<*>): Boolean = this is ParameterizedType && isCollection() && isParameterizedWith(aClass)