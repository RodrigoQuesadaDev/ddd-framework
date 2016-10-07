@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification

/**
 * Created by Rodrigo Quesada on 04/10/16.
 */
class ValidClassImplementationResult(val valid: Boolean, override val clazz: Class<*>) : ClassVerificationResult

internal inline fun List<ValidClassImplementationResult>.valid() = filter { it.valid }

internal inline fun List<ValidClassImplementationResult>.invalid() = filter { !it.valid }