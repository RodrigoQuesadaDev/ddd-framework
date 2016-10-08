package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.correct_implementations

import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.ValueObject

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
//region Parents
@ValueObject
internal open class CorrectParentValueObject {
    private val value1: Int = 1
    private val value2: Int = 2
}

internal open class CompatibleNonValueObjectParent {
    private val value1: Int = 1
    private val value2: Int = 2
}

@ValueObject
internal interface ValueObjectParentInterface
//endregion

//region Children
internal open class NonAnnotatedCorrectChildWithCorrectParentValueObject : CorrectParentValueObject() {
    private val value4: Int = 4
    private val value5: Int = 5
}

internal open class NonAnnotatedCorrectChildWithValueObjectParentInterface : ValueObjectParentInterface {
    private val value4: Int = 4
    private val value5: Int = 5
}

@ValueObject
internal open class CorrectChildWithCompatibleNonValueObjectParent : CompatibleNonValueObjectParent() {
    private val value4: Int = 4
    private val value5: Int = 5
}
//endregion

//region Grandchildren
internal class NonAnnotatedCorrectGrandchildWithCorrectGrandparentValueObject : NonAnnotatedCorrectChildWithCorrectParentValueObject() {
    private val value7: Int = 7
    private val value8: Int = 8
}

internal class NonAnnotatedCorrectGrandchildWithValueObjectGrandparentInterface : NonAnnotatedCorrectChildWithValueObjectParentInterface() {
    private val value7: Int = 7
    private val value8: Int = 8
}

@ValueObject
internal class CorrectGrandchildWithCompatibleNonValueObjectGrandparent : CorrectChildWithCompatibleNonValueObjectParent() {
    private val value7: Int = 7
    private val value8: Int = 8
}
//endregion