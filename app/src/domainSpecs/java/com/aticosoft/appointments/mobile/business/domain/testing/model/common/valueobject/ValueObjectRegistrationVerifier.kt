@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.testing.model.common.valueobject

import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.ClassRegistrationResult
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.ClassVerifier
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.valueobject.ValueObjectsManager
import com.google.auto.factory.Provided

/**
 * Created by Rodrigo Quesada on 06/12/15.
 */
//@AutoFactory
internal class ValueObjectRegistrationVerifier(
        @Provided valueObjectsManager: ValueObjectsManager, packagePaths: Array<String>
) : ClassVerifier<ClassRegistrationResult> {

    override val _genericVerifier = object : GenericValueObjectVerifier<ClassRegistrationResult>(packagePaths) {

        override fun Class<*>.verify() = ClassRegistrationResult(isRegistered(), this)
    }

    private val registeredValueObjects: Set<Class<*>> = valueObjectsManager.registeredValueObjects.toSet()

    //region Verification
    private inline fun Class<*>.isRegistered() = registeredValueObjects.contains(this)
    //endregion
}