Narrative:

As a developer
I want to automatically verify the implementation of ValueObject classes
So that I can always have them correctly implemented

Scenario: set-up

Given I run the code that verifies the correct definition of ValueObject classes with the configuration

{
    packageNames: [
        'com.aticosoft.appointments.mobile.business.domain.model',
        'com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.wrong_implementations',
        'com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.correct_implementations'
    ]
}

Scenario: incorrect implementations

Then the system detects that the next classes are not correctly implemented:

| className                                                                                                                                                          |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.wrong_implementations.IncorrectParentValueObject                             |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.wrong_implementations.NonAnnotatedCorrectChildWithIncorrectParentValueObject |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.wrong_implementations.NonAnnotatedIncorrectChildWithCorrectParentValueObject |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.wrong_implementations.CorrectChildWithIncompatibleNonValueObjectParent       |

Scenario: correct implementations

Then the system detects that the next classes are correctly implemented:

| className                                                                                                                                                            |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.correct_implementations.CorrectParentValueObject                               |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.correct_implementations.NonAnnotatedCorrectChildWithCorrectParentValueObject   |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.correct_implementations.CorrectChildWithCompatibleNonValueObjectParent         |

Scenario: project's base package

Then the system detects there are value objects under com.aticosoft.appointments.mobile.business.domain.model and all are correctly implemented
