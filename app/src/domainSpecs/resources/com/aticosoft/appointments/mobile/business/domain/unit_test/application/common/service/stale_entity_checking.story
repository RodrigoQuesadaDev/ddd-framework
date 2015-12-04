Narrative:

As a developer
I want to make sure the system fail when an stale entity is passed to an application service
So that the user actions are never based on stale data

Scenario: set-up stale entity

Given entities [1, 3 ,5]
When I get entity with value 3 and keep it for later use
And I call an application service that modifies its value stored in the database

Scenario: a stale entity is passed using a simple field

When I pass that entity to an application service that uses it without modifying it, using a simple field
Then the system throws an exception indicating it's stale

Scenario: a stale entity is passed using a collection field

When I pass that entity to an application service that uses it without modifying it, along entities [1, 5] and using <usageType>
Then the system throws an exception indicating it's stale

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/application/common/service/passed_entities/usage-types.table

Scenario: set-up removed entity

Given entities [1, 3 ,5]
When I get entity with value 3 and keep it for later use
And I call an application service that removes it

Scenario: a stale entity is passed using a simple field

When I pass that entity to an application service that uses it without modifying it, using a simple field
Then the system throws an exception indicating that it doesn't exist anymore

Scenario: a stale entity is passed using a collection field

When I pass that entity to an application service that uses it without modifying it, along entities [1, 5] and using <usageType>
Then the system throws an exception indicating that it doesn't exist anymore

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/application/common/service/passed_entities/usage-types.table