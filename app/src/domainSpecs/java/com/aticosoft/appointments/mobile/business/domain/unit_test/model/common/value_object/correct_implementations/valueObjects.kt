package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.correct_implementations

import com.aticosoft.appointments.mobile.business.domain.model.common.value_object.ValueObject
import com.rodrigodev.common.properties.Delegates.readOnly
import com.rodrigodev.common.properties.Delegates.writeOnce

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
@ValueObject
internal open class CorrectParentValueObject {
    private val value1: Int by readOnly(1)
    private val value2: Int by writeOnce()
}

internal open class CompatibleNonValueObject {
    private val value1: Int by readOnly(1)
    private val value2: Int by writeOnce()
}

internal class NonAnnotatedCorrectChildWithCorrectParentValueObject : CorrectParentValueObject() {
    private val value4: Int by readOnly(4)
    private val value5: Int by writeOnce()
}

@ValueObject
internal class CorrectChildWithCompatibleNonValueObjectParent : CompatibleNonValueObject() {
    private val value4: Int by readOnly(4)
    private val value5: Int by writeOnce()
}