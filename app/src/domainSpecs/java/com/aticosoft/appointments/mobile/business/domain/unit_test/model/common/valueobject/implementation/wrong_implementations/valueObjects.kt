package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations

import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.ValueObject
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.correct_implementations.CorrectParentValueObject
import com.rodrigodev.common.properties.Delegates.readOnly

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
@ValueObject
internal open class TransientFieldParentValueObject {
    private val value1: Int = 1
    @Transient private val value2: Int = 2
    private val value3: Int = 3
}

@ValueObject
internal open class DelegatedFieldParentValueObject {
    private val value1: Int = 1
    private val value2: Int by readOnly(2)
    private val value3: Int = 3
}

internal open class TransientFieldNonValueObject {
    private val value1: Int = 1
    @Transient private val value2: Int = 2
    private val value3: Int = 3
}

internal open class DelegatedFieldNonValueObject {
    private val value1: Int = 1
    private val value2: Int by readOnly(2)
    private val value3: Int = 3
}

internal class NonAnnotatedCorrectChildWithTransientFieldParentValueObject : TransientFieldParentValueObject() {
    private val value4: Int = 4
    private val value5: Int = 5
}

internal class NonAnnotatedCorrectChildWithDelegatedFieldParentValueObject : DelegatedFieldParentValueObject() {
    private val value4: Int = 4
    private val value5: Int = 5
}

internal class NonAnnotatedTransientFieldChildWithCorrectParentValueObject : CorrectParentValueObject() {
    private val value4: Int = 4
    @Transient private val value5: Int = 5
    private val value6: Int = 6
}

internal class NonAnnotatedDelegatedFieldChildWithCorrectParentValueObject : CorrectParentValueObject() {
    private val value4: Int = 4
    private val value5: Int by readOnly(5)
    private val value6: Int = 6
}

@ValueObject
internal class CorrectChildWithTransientFieldNonValueObjectParent : TransientFieldNonValueObject() {
    private val value4: Int = 4
    private val value5: Int = 5
}

@ValueObject
internal class CorrectChildWithDelegatedFieldNonValueObjectParent : DelegatedFieldNonValueObject() {
    private val value4: Int = 4
    private val value5: Int = 5
}