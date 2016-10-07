Value objects cannot have any transient field or delegated property.

Narrative:

As a developer
I want to automatically verify the implementation of ValueObject classes
So that I can always have them correctly implemented

Scenario: set-up

Given I run the code that verifies the correct definition of ValueObject classes with the next configuration:
{
    packageNames: ['com.aticosoft.appointments.mobile.business.domain']
}

Scenario: incorrect implementations

Then the system detects that only the next classes are not correctly implemented:

| className                                                                                                                                                                             |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations.TransientFieldParentValueObject                             |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations.DelegatedFieldParentValueObject                             |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations.NonAnnotatedCorrectChildWithTransientFieldParentValueObject |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations.NonAnnotatedCorrectChildWithDelegatedFieldParentValueObject |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations.NonAnnotatedTransientFieldChildWithCorrectParentValueObject |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations.NonAnnotatedDelegatedFieldChildWithCorrectParentValueObject |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations.CorrectChildWithTransientFieldNonValueObjectParent          |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations.CorrectChildWithDelegatedFieldNonValueObjectParent          |

Scenario: correct implementations

Then the system detects that the next classes are correctly implemented:

| className                                                                                                                                                                        |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.correct_implementations.CorrectParentValueObject                             |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.correct_implementations.NonAnnotatedCorrectChildWithCorrectParentValueObject |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.correct_implementations.CorrectChildWithCompatibleNonValueObjectParent       |

Scenario: project's base package

Then the system detects there are value objects under com.aticosoft.appointments.mobile.business.domain.model and all are correctly implemented