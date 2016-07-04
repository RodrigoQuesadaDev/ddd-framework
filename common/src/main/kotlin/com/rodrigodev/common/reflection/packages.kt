@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.reflection

/**
 * Created by Rodrigo Quesada on 06/12/15.
 */
fun Package.isContainedUnder(packageName: String) = name.let { containedPackageName ->
    containedPackageName.contains(packageName) && containedPackageName.substring(packageName.length).startsWith('.')
}