@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification

import com.rodrigodev.common.nullability.nonNullOr
import com.rodrigodev.common.reflection.isContainedUnder

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */
internal interface ClassVerificationResult {
    val clazz: Class<*>
}

internal inline fun <R : ClassVerificationResult> List<R>.classes() = map { it.clazz }

internal inline fun <R : ClassVerificationResult> List<R>.classNames() = classes().map { it.canonicalName }

internal inline fun <R : ClassVerificationResult> List<R>.packages() = classes().map { it.`package` }

internal inline fun <R : ClassVerificationResult> List<R>.containedUnder(packageName: String, excludingPackageName: String? = null)
        = filter { it.clazz.isContainedUnder(packageName) && excludingPackageName.nonNullOr(true) { !it.clazz.isContainedUnder(this@nonNullOr) } }