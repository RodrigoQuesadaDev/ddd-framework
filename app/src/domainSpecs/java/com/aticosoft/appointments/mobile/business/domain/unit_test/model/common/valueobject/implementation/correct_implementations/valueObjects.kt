package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.correct_implementations

import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.ValueObject

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
@ValueObject
internal open class CorrectParentValueObject {
    private val value1: Int = 1
    private val value2: Int = 2
}

internal open class CompatibleNonValueObject {
    private val value1: Int = 1
    private val value2: Int = 2
}

internal class NonAnnotatedCorrectChildWithCorrectParentValueObject : CorrectParentValueObject() {
    private val value4: Int = 4
    private val value5: Int = 5
}

@ValueObject
internal class CorrectChildWithCompatibleNonValueObjectParent : CompatibleNonValueObject() {
    private val value4: Int = 4
    private val value5: Int = 5
}