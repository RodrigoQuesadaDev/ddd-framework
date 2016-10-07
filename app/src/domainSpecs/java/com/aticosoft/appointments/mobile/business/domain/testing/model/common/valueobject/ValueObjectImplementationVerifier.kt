@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.testing.model.common.valueobject

import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.ClassVerifier
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.ValidClassImplementationResult
import com.rodrigodev.common.nullability.nonNullOr
import com.rodrigodev.common.reflection.allMemberProperties
import com.rodrigodev.common.reflection.delegate
import com.rodrigodev.common.reflection.isKotlinClass
import java.lang.reflect.Modifier
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaField

/**
 * Created by Rodrigo Quesada on 06/12/15.
 */
internal class ValueObjectImplementationVerifier(packagePaths: Array<String>) : ClassVerifier<ValidClassImplementationResult> {

    override val _genericVerifier = object : GenericValueObjectVerifier<ValidClassImplementationResult>(packagePaths) {

        override fun Class<*>.verify() = ValidClassImplementationResult(isValid(), this)
    }
}

//region Validation
private inline fun Class<*>.isValid() = isKotlinClass() && kotlin.allMemberProperties.asSequence().all { it.isValid() }

private inline fun KProperty1<*, *>.isValid(): Boolean = isNotTransient() && isNotDelegated()

private inline fun KProperty1<*, *>.isNotTransient(): Boolean = !isTransient()

private inline fun KProperty1<*, *>.isTransient(): Boolean = javaField.nonNullOr(false) { Modifier.isTransient(modifiers) }

private inline fun KProperty1<*, *>.isNotDelegated(): Boolean = delegate() == null
//endregion