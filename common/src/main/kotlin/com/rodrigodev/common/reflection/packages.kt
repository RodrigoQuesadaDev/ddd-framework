@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.reflection

/**
 * Created by Rodrigo Quesada on 06/12/15.
 */
inline fun Package.isContainedUnder(packageName: String) = name.let { containedPackageName ->
    containedPackageName.contains(packageName) && (containedPackageName.length == packageName.length || containedPackageName.substring(packageName.length).startsWith('.'))
}

inline fun Package.isContainedUnder(`package`: Package) = isContainedUnder(`package`.name)