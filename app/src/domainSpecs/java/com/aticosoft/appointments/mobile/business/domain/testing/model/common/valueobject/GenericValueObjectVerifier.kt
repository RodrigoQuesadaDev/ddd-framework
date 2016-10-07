package com.aticosoft.appointments.mobile.business.domain.testing.model.common.valueobject

import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.ValueObject
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.ClassVerificationResult
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.GenericClassVerifier
import org.reflections.Reflections

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */
internal abstract class GenericValueObjectVerifier<R : ClassVerificationResult>(packagePaths: Array<String>) : GenericClassVerifier<R>(packagePaths) {

    override fun Reflections.retrieveClasses() = getTypesAnnotatedWith(ValueObject::class.java)
}