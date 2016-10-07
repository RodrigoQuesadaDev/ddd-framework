Value objects should get registered by the framework.

Scenario: set-up

Given I run the code that verifies ValueObject classes are registered by the framework, using the next configuration:
{
    packageNames: ['com.aticosoft.appointments.mobile.business.domain']
}

Scenario: not registered

Then the system detects that only the next classes are not registered:

| className                                                                                                                  |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.registration.test_data.NonRegisteredValueObject |

Scenario: correctly registered

Then the system detects that the next classes are correctly registered:

| className                                                                                                               |
| com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.registration.test_data.RegisteredValueObject |

Scenario: registered classes are value objects

Then the system detects that all classes registered as value objects are effectively value objects

Scenario: project's base package

Then the system detects there are value objects under com.aticosoft.appointments.mobile.business.domain.model and all are correctly registered