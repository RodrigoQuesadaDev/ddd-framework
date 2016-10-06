package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.wrong_implementations

import com.aticosoft.appointments.mobile.business.domain.model.common.value_object.ValueObject
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.correct_implementations.CorrectParentValueObject
import com.rodrigodev.common.properties.Delegates.readOnly
import com.rodrigodev.common.properties.Delegates.writeOnce

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
@ValueObject
internal open class IncorrectParentValueObject {
    private val value1: Int by readOnly(1)
    private val value2: Int = 2
    private val value3: Int by writeOnce()
}

internal open class IncompatibleNonValueObject {
    private val value1: Int by readOnly(1)
    private val value2: Int = 2
    private val value3: Int by writeOnce()
}

internal class NonAnnotatedCorrectChildWithIncorrectParentValueObject : IncorrectParentValueObject() {
    private val value4: Int by readOnly(4)
    private val value5: Int by writeOnce()
}

internal class NonAnnotatedIncorrectChildWithCorrectParentValueObject : CorrectParentValueObject() {
    private val value4: Int by readOnly(4)
    private val value5: Int = 5
    private val value6: Int by writeOnce()
}

@ValueObject
internal class CorrectChildWithIncompatibleNonValueObjectParent : IncompatibleNonValueObject() {
    private val value4: Int by readOnly(4)
    private val value5: Int by writeOnce()
}