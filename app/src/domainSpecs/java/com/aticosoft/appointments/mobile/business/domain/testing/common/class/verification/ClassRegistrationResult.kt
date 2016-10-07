@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification

/**
 * Created by Rodrigo Quesada on 04/10/16.
 */
class ClassRegistrationResult(val registered: Boolean, override val clazz: Class<*>) : ClassVerificationResult

internal inline fun List<ClassRegistrationResult>.registered() = filter { it.registered }

internal inline fun List<ClassRegistrationResult>.notRegistered() = filter { !it.registered }