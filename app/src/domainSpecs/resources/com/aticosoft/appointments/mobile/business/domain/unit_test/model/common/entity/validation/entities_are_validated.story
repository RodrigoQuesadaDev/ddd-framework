Narrative:

As a developer
I want to be able to define validation rules for the entities
So that I can have them automatically validated when using them

Scenario: unsuccessful creation

Given no data
And the next step might throw an exception
When an <entityType> entity with <validationType> validation is created with the values <testDataValues>
Then the system throws a <validationExceptionType> with the message "<errorMessage>"

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/model/common/entity/validation/unsuccessful_entries.table

Scenario: successful creation

Given no data
When an <entityType> entity with <validationType> validation is created with the values <testDataValues>
!-- TODO fix later, not so relevant right now (2016-06-21)
!-- Then validation runs only once per each field/class it validates
!-- No exception was thrown

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/model/common/entity/validation/successful_entries.table

Scenario: unsuccessful update

Given a valid <entityType> entity with <validationType> validation
And the next step might throw an exception
When the entity is updated with the values <testDataValues>
Then the system throws a <validationExceptionType> with the message "<errorMessage>"
And the entity is not updated

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/model/common/entity/validation/unsuccessful_entries.table

Scenario: successful update

Given a valid <entityType> entity with <validationType> validation
When the entity is updated with the values <testDataValues>
Then validation runs only once per each field/class it validates
!-- No exception was thrown

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/model/common/entity/validation/successful_entries.table

Scenario: non-validated fields are updated

Given a valid <entityType> entity with PRIME_NUMBER_AND_GMAIL validation
When one of the entity's fields that are not validated is updated
Then the validation doesn't run

Examples:
| entityType |
| PARENT     |
| CHILD      |