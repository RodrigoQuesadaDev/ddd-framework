@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.reflection

import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaMethod

/**
 * Created by Rodrigo Quesada on 05/10/16.
 */
inline fun <T, R> KProperty1<T, R>.usesDelegate(delegateClass: KClass<*>): Boolean = delegate()?.type?.isSubOfOrSameAs(delegateClass.java) ?: false

inline fun <T> KProperty<T>.delegate(): Field? = try {
    getter.javaMethod?.declaringClass?.getDeclaredField("$name\$delegate")
}
catch(e: NoSuchFieldException) {
    null
}