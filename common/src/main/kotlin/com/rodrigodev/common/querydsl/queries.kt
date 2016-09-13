@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.querydsl

import com.querydsl.core.types.EntityPath

/**
 * Created by Rodrigo Quesada on 12/09/16.
 */
inline fun <T> entityPathFor(aClass: Class<T>): EntityPath<T> = with(aClass) {
    val entityPathClass = classLoader.loadClass("${`package`.name}.Q$simpleName")
    val entityPathField = entityPathClass.getDeclaredField(simpleName.decapitalize())
    @Suppress("UNCHECKED_CAST")
    return entityPathField.get(null) as EntityPath<T>
}