Narrative:

As a developer
I want to make sure the entities passed to application services are never dirty
So that they are never modified outside the domain layer

Scenario: set-up

Given entities [1, 3 ,5]
When I get entity with value 3, modify it and then keep it for later use

Scenario: a dirty entity is passed using a simple field

Given the next step might throw an exception
When I call an application service passing that entity to it, using <usageType>
Then the system throws an exception indicating it's dirty

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/application/common/service/passed_entities/simple-usage-types.table

Scenario: a dirty entity is passed using a collection field

Given the next step might throw an exception
When I call an application service passing it that entity along entities [1, 5], using <usageType>
Then the system throws an exception indicating it's dirty

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/application/common/service/passed_entities/collection-usage-types.table