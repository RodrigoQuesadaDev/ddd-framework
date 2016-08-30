@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.reflection

import java.lang.reflect.Type

/**
 * Created by Rodrigo Quesada on 30/06/16.
 */
private val KOTLIN_METADATA_ANNOTATION_NAME = "kotlin.Metadata"

inline fun Class<*>.isSuperOfOrSameAs(aClass: Class<*>) = isAssignableFrom(aClass)

inline fun Class<*>.isSubOfOrSameAs(aClass: Class<*>) = aClass.isSuperOfOrSameAs(this)

inline fun Iterable<Class<*>>.anyIsSuperOfOrSameAs(aClass: Class<*>) = any { it.isSuperOfOrSameAs(aClass) }

inline fun Iterable<Class<*>>.anyIsSubOfOrSameAs(aClass: Class<*>) = any { it.isSubOfOrSameAs(aClass) }

fun Class<*>.isKotlinClass() = declaredAnnotations.any { it.annotationClass.java.name == KOTLIN_METADATA_ANNOTATION_NAME }

fun Class<*>.genericAncestors(): Array<Type> = if (isInterface) genericInterfaces else arrayOf(genericSuperclass)

//region Utils
//TODO move inside genericAncestor function when KT-9874/KT-8199 is resolved
private class TypeInfo(
        ancestorClass: Class<*>,
        val type: Type,
        val subOfAncestor: Class<*>? = type.classes().firstOrNull { c -> c.isSubOfOrSameAs(ancestorClass) }
)
//endregion

fun Class<*>.genericAncestor(ancestorClass: Class<*>): Type {


    require(isSubOf(ancestorClass), { "This class should be a subclass of the passed one." })

    var typeInfo: TypeInfo = TypeInfo(this, this)
    do {
        typeInfo = typeInfo.subOfAncestor!!.genericAncestors().asSequence()
                .map { TypeInfo(ancestorClass, it) }
                .first { it.subOfAncestor != null }
    } while (typeInfo.subOfAncestor != ancestorClass)

    return typeInfo.type
}

//region Utils
private inline fun Class<*>.isSubOf(aClass: Class<*>) = isSubOfOrSameAs(aClass) && this != aClass

private inline fun Array<Type>.toClasses() = flatMap { it.classes() }
//endregion