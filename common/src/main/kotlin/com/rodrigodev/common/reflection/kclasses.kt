@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.reflection

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.declaredMemberProperties

/**
 * Created by Rodrigo Quesada on 30/06/16.
 */
inline fun KClass<*>.isSuperOfOrSameAs(anotherClass: Class<*>) = this.java.isAssignableFrom(anotherClass)

inline fun Array<out KClass<*>>.anyIsSuperOfOrSameAs(anotherClass: Class<*>) = any { it.isSuperOfOrSameAs(anotherClass) }

inline fun Array<out KClass<*>>.anyIsSuperOfOrSameAsAny(otherClasses: List<Class<*>>) = otherClasses.any { this.anyIsSuperOfOrSameAs(it) }

/**
 * Returns all non-extension properties declared in this class and all of its superclasses,
 * including private member properties.
 */
val KClass<*>.allMemberProperties: Collection<KProperty1<out Any, *>>
    get() = retrieveAllMemberProperties().toList()

private fun KClass<*>.retrieveAllMemberProperties(): Sequence<KProperty1<out Any, *>> {
    var allMemberProperties = declaredMemberProperties.asSequence()

    val superClass = java.superclass
    @Suppress("UNCHECKED_CAST")
    if (superClass.isKotlinClass() && superClass != Any::class.java) allMemberProperties += (superClass as Class<Any>).kotlin.retrieveAllMemberProperties()

    return allMemberProperties
}