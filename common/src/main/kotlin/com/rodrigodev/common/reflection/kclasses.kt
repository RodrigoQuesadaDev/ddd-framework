@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.reflection

import kotlin.reflect.KClass

/**
 * Created by Rodrigo Quesada on 30/06/16.
 */
inline fun KClass<*>.isSuperOfOrSameAs(anotherClass: Class<*>) = this.java.isAssignableFrom(anotherClass)

inline fun Array<out KClass<*>>.anyIsSuperOfOrSameAs(anotherClass: Class<*>) = any { it.isSuperOfOrSameAs(anotherClass) }

inline fun Array<out KClass<*>>.anyIsSuperOfOrSameAsAny(otherClasses: List<Class<*>>) = otherClasses.any { this.anyIsSuperOfOrSameAs(it) }