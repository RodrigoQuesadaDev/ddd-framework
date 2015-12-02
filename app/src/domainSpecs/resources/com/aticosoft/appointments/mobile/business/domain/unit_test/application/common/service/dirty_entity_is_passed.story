Narrative:

As a developer
I want to make sure the entities passed to application services are never dirty
So that they are never modified outside the domain layer

Scenario: scenario description

Given entities [1, 3 ,5]
When I get entity with value 3
And I modify its value outside the domain layer
And I call an application service passing that entity to it
Then the system throws an exception indicating it's dirty