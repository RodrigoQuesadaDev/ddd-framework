Narrative:

As a developer
I want to automatically verify the implementation of Command classes
So that I can always have them correctly implemented

Scenario: set-up

Given I run the code that verifies the correct definition of entities on Command classes with the next configuration:
{
    packageNames: ['com.aticosoft.appointments.mobile.business.domain']
}

Scenario: incorrect implementations

Then the system detects that only the next classes are not correctly implemented:

| className                                                                                                                                                      |
|-- simple                                                                                                                                                     --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.NestedCommandOnPropertyCommand            |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnPropertyCommand                   |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnPropertyOfParentCommand           |
|-- generic                                                                                                                                                    --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.NestedCommandOnGenericPropertyCommand     |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnGenericPropertyCommand            |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnGenericPropertyOfParentCommand    |
|-- array                                                                                                                                                      --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.NestedCommandOnArrayCommand               |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnArrayCommand                      |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnArrayOfParentCommand              |
|-- list                                                                                                                                                       --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.NestedCommandOnListCommand                |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnListCommand                       |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnListOfParentCommand               |
|-- set                                                                                                                                                        --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.NestedCommandOnSetCommand                 |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnSetCommand                        |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnSetOfParentCommand                |
|-- map as value                                                                                                                                               --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.NestedCommandOnMapAsValueCommand          |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnMapAsValueCommand                 |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnMapAsValueOfParentCommand         |
|-- map as key                                                                                                                                                 --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.NestedCommandOnMapAsKeyCommand            |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnMapAsKeyCommand                   |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnMapAsKeyOfParentCommand           |
|-- map as value and key                                                                                                                                       --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.NestedCommandsOnMapAsValueAndKeyCommand   |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntitiesOnMapAsValueAndKeyCommand         |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntitiesOnMapAsValueAndKeyOfParentCommand |

Scenario: correct implementations

Then the system detects that the next classes are correctly implemented:

| className                                                                                                                                                        |
|-- simple                                                                                                                                                       --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.NestedCommandOnPropertyCommand            |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnPropertyCommand                   |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnPropertyOfParentCommand           |
|-- generic                                                                                                                                                      --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.NestedCommandOnGenericPropertyCommand     |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnGenericPropertyCommand            |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnGenericPropertyOfParentCommand    |
|-- array                                                                                                                                                        --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.NestedCommandOnArrayCommand               |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnArrayCommand                      |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnArrayOfParentCommand              |
|-- list                                                                                                                                                         --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.NestedCommandOnListCommand                |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnListCommand                       |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnListOfParentCommand               |
|-- set                                                                                                                                                          --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.NestedCommandOnSetCommand                 |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnSetCommand                        |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnSetOfParentCommand                |
|-- map as value                                                                                                                                                 --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.NestedCommandOnMapAsValueCommand          |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnMapAsValueCommand                 |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnMapAsValueOfParentCommand         |
|-- map as key                                                                                                                                                   --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.NestedCommandOnMapAsKeyCommand            |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnMapAsKeyCommand                   |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnMapAsKeyOfParentCommand           |
|-- map as value and key                                                                                                                                         --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.NestedCommandsOnMapAsValueAndKeyCommand   |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntitiesOnMapAsValueAndKeyCommand         |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntitiesOnMapAsValueAndKeyOfParentCommand |

Scenario: project's base package

Then the system detects there are commands under com.aticosoft.appointments.mobile.business.domain.application and all are correctly implemented