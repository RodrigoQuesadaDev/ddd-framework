Narrative:

As a developer
I want to make sure the entities passed to application services are always in detached state
So that they never mistakenly cause en new record to be created in the database for it

Scenario: a new entity is passed using a simple field

Given entities [1, 5]
And the next step might throw an exception
When I create a new entity and pass it to an application service, using <usageType>
Then the system throws an exception indicating it's not detached

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/application/common/service/passed_entities/simple-usage-types.table

Scenario: a new entity is passed using a collection field

Given the next step might throw an exception
When I create a new entity and pass it to an application service along entities [1, 5], using <usageType>
Then the system throws an exception indicating it's not detached

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/application/common/service/passed_entities/collection-usage-types.table