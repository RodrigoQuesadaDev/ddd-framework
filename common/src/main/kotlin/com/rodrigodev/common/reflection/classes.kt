@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.reflection

import org.apache.commons.lang3.reflect.TypeUtils
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable

/**
 * Created by Rodrigo Quesada on 30/06/16.
 */
private val KOTLIN_METADATA_ANNOTATION_NAME = "kotlin.Metadata"

inline fun Class<*>.isSuperOfOrSameAs(aClass: Class<*>) = isAssignableFrom(aClass)

inline fun Class<*>.isSubOfOrSameAs(aClass: Class<*>) = aClass.isSuperOfOrSameAs(this)

inline fun Class<*>.isContainedUnder(packageName: String) = `package`.isContainedUnder(packageName)

inline fun Class<*>.isContainedUnder(`package`: Package) = isContainedUnder(`package`.name)

inline fun Iterable<Class<*>>.anyIsSuperOfOrSameAs(aClass: Class<*>) = any { it.isSuperOfOrSameAs(aClass) }

inline fun Iterable<Class<*>>.anyIsSubOfOrSameAs(aClass: Class<*>) = any { it.isSubOfOrSameAs(aClass) }

fun Class<*>.isKotlinClass() = declaredAnnotations.any { it.annotationClass.java.name == KOTLIN_METADATA_ANNOTATION_NAME }

inline fun Class<*>.genericAncestors(): Array<Type> = if (isInterface) genericInterfaces else arrayOf(genericSuperclass)

//region Utils
//TODO move inside genericAncestor function when KT-9874/KT-8199 is resolved
private class TypeInfo(
        ancestorClass: Class<*>,
        val type: Type,
        val subOfAncestor: Class<*>? = type.classes().firstOrNull { c -> c.isSubOfOrSameAs(ancestorClass) }
)
//endregion

inline fun Class<*>.hierarchyTypeArgumentsMap(ancestorClass: Class<*>): Map<TypeVariable<*>, Type> = TypeUtils.getTypeArguments(this, ancestorClass)

/*
* This method uses hierarchyTypeArgumentsMap, so if you are going to query more than 1 type
* argument, then better use hierarchyTypeArgumentsMap directly, performance should be better that
* way.
* */
inline fun Class<*>.typeArgument(ancestorClass: Class<*>, argumentIndex: Int): Type? = hierarchyTypeArgumentsMap(ancestorClass)[ancestorClass.typeParameters[argumentIndex]]

fun <A : Annotation> Class<*>.getAnnotation(annotationType: Class<A>, honorInherited: Boolean): A? = if (honorInherited) getAnnotation(annotationType)
else {
    var annotation = getDeclaredAnnotation(annotationType)

    if (annotation == null) annotation = superclass?.getAnnotation(annotationType, honorInherited)
            ?: interfaces.asSequence()
            .map { it.getAnnotation(annotationType, honorInherited) }
            .filterNotNull()
            .firstOrNull()

    annotation
}

//region Utils
private inline fun Class<*>.isSubOf(aClass: Class<*>) = isSubOfOrSameAs(aClass) && this != aClass

private inline fun Array<Type>.toClasses() = flatMap { it.classes() }
//endregion