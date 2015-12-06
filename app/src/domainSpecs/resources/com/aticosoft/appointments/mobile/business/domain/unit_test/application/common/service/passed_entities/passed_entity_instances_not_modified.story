Narrative:

As a developer
I want to make sure the system only modifies copies of the entity instances passed
So that the instances used on the presentation layer are never modified/dirty

Scenario: an entity is passed using a simple field

Given entities [1, 3 ,5]
When I get entity with value 3 and keep it for later use
When I pass the kept instance to an application service that modifies it, using <usageType>
Then the instance was never modified by the called service

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/application/common/service/passed_entities/simple-usage-types.table

Scenario: an entity is passed using a collection field

Given entities [1, 3 ,5]
When I get entity with value 3 and keep it for later use
When I pass the kept instance to an application service that modifies it along entities [1, 5], using <usageType>
Then the instance was never modified by the called service

Examples:
com/aticosoft/appointments/mobile/business/domain/unit_test/application/common/service/passed_entities/collection-usage-types.table