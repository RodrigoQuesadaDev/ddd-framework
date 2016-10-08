package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations

import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.ValueObject
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.correct_implementations.CorrectParentValueObject
import com.rodrigodev.common.properties.Delegates.readOnly

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
//region Parents
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

internal open class TransientFieldNonValueObjectParent {
    private val value1: Int = 1
    @Transient private val value2: Int = 2
    private val value3: Int = 3
}

internal open class DelegatedFieldNonValueObjectParent {
    private val value1: Int = 1
    private val value2: Int by readOnly(2)
    private val value3: Int = 3
}
//endregion

//region Children
internal open class NonAnnotatedCorrectChildWithTransientFieldParentValueObject : TransientFieldParentValueObject() {
    private val value4: Int = 4
    private val value5: Int = 5
}

internal open class NonAnnotatedCorrectChildWithDelegatedFieldParentValueObject : DelegatedFieldParentValueObject() {
    private val value4: Int = 4
    private val value5: Int = 5
}

internal open class NonAnnotatedTransientFieldChildWithCorrectParentValueObject : CorrectParentValueObject() {
    private val value4: Int = 4
    @Transient private val value5: Int = 5
    private val value6: Int = 6
}

internal open class NonAnnotatedDelegatedFieldChildWithCorrectParentValueObject : CorrectParentValueObject() {
    private val value4: Int = 4
    private val value5: Int by readOnly(5)
    private val value6: Int = 6
}

@ValueObject
internal class CorrectChildWithTransientFieldNonValueObjectParent : TransientFieldNonValueObjectParent() {
    private val value4: Int = 4
    private val value5: Int = 5
}

@ValueObject
internal class CorrectChildWithDelegatedFieldNonValueObjectParent : DelegatedFieldNonValueObjectParent() {
    private val value4: Int = 4
    private val value5: Int = 5
}
//endregion

//region Grandchildren
internal class NonAnnotatedCorrectGrandchildWithTransientFieldGrandparentValueObject : NonAnnotatedCorrectChildWithTransientFieldParentValueObject() {
    private val value7: Int = 7
    private val value8: Int = 8
}

internal class NonAnnotatedCorrectGrandchildWithDelegatedFieldGrandparentValueObject : NonAnnotatedCorrectChildWithDelegatedFieldParentValueObject() {
    private val value7: Int = 7
    private val value8: Int = 8
}

internal class NonAnnotatedTransientFieldGrandchildWithCorrectGrandparentValueObject : NonAnnotatedCorrectChildWithTransientFieldParentValueObject() {
    private val value7: Int = 7
    @Transient private val value8: Int = 8
    private val value9: Int = 9
}

internal class NonAnnotatedDelegatedFieldGrandchildWithCorrectGrandparentValueObject : NonAnnotatedCorrectChildWithDelegatedFieldParentValueObject() {
    private val value7: Int = 7
    private val value8: Int by readOnly(8)
    private val value9: Int = 9
}

@ValueObject
internal class CorrectGrandchildWithTransientFieldNonValueObjectGrandparent : NonAnnotatedCorrectChildWithTransientFieldParentValueObject() {
    private val value7: Int = 7
    private val value8: Int = 8
}

@ValueObject
internal class CorrectGrandchildWithDelegatedFieldNonValueObjectGrandparent : NonAnnotatedCorrectChildWithDelegatedFieldParentValueObject() {
    private val value7: Int = 7
    private val value8: Int = 8
}
//endregion