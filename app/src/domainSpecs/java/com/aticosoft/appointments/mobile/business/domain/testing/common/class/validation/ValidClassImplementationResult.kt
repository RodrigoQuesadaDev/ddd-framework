package com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.validation

import com.rodrigodev.common.reflection.isContainedUnder

/**
 * Created by Rodrigo Quesada on 04/10/16.
 */
class ValidClassImplementationResult(val valid: Boolean, val clazz: Class<*>)

internal inline fun List<ValidClassImplementationResult>.valid() = filter { it.valid }

internal inline fun List<ValidClassImplementationResult>.invalid() = filter { !it.valid }

internal inline fun List<ValidClassImplementationResult>.classes() = map { it.clazz }

internal inline fun List<ValidClassImplementationResult>.classNames() = classes().map { it.canonicalName }

internal inline fun List<ValidClassImplementationResult>.packages() = classes().map { it.`package` }

internal inline fun List<ValidClassImplementationResult>.containedUnder(packageName: String) = filter { it.clazz.`package`.isContainedUnder(packageName) }