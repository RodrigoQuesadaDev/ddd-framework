Narrative:

As a developer
I want to automatically verify the implementation of Command classes
So that I can always have them correctly implemented

Scenario: set-up

Given I run the code that verifies the correct definition of entities on Command classes with the configuration

{
    packageNames: [
        'com.aticosoft.appointments.mobile.business.domain.application',
        'com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations',
        'com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations'
    ]
}

Scenario: incorrect implementations

Then the system detects that the next classes are not correctly implemented:

| className                                                                                                                                                      |
|-- simple                                                                                                                                                     --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnPropertyCommand                   |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnPropertyOfParentCommand           |
|-- generic                                                                                                                                                    --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnGenericPropertyCommand            |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnGenericPropertyOfParentCommand    |
|-- list                                                                                                                                                       --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnListCommand                       |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnListOfParentCommand               |
|-- set                                                                                                                                                        --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnSetCommand                        |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnSetOfParentCommand                |
|-- map as value                                                                                                                                               --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnMapAsValueCommand                 |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnMapAsValueOfParentCommand         |
|-- map as key                                                                                                                                                 --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnMapAsKeyCommand                   |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntityOnMapAsKeyOfParentCommand           |
|-- map as value and key                                                                                                                                       --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntitiesOnMapAsValueAndKeyCommand         |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations.EntitiesOnMapAsValueAndKeyOfParentCommand |

Scenario: correct implementations

Then the system detects that the next classes are correctly implemented:

| className                                                                                                                                                        |
|-- simple                                                                                                                                                       --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnPropertyCommand                   |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnPropertyOfParentCommand           |
|-- generic                                                                                                                                                      --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnGenericPropertyCommand            |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnGenericPropertyOfParentCommand    |
|-- list                                                                                                                                                         --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnListCommand                       |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnListOfParentCommand               |
|-- set                                                                                                                                                          --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnSetCommand                        |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnSetOfParentCommand                |
|-- map as value                                                                                                                                                 --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnMapAsValueCommand                 |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnMapAsValueOfParentCommand         |
|-- map as key                                                                                                                                                   --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnMapAsKeyCommand                   |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntityOnMapAsKeyOfParentCommand           |
|-- map as value and key                                                                                                                                         --|
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntitiesOnMapAsValueAndKeyCommand         |
| com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations.EntitiesOnMapAsValueAndKeyOfParentCommand |

Scenario: project's base package

Then the system detects there are commands under com.aticosoft.appointments.mobile.business.domain.application and all are correctly implemented