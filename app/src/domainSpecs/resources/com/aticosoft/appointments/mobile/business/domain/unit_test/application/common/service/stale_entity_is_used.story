Narrative:

As a developer
I want to make sure the system fail when an stale entity is passed to an application service
So that the user actions are never based on stale data

Scenario: stale entity is passed

Given entities [1, 3 ,5]
When I get entity with value 3 and keep it somewhere for later usage
And I call an application service that modifies its value stored in the database
And I call an application service that only uses it but never modifies the database
Then the system throws an exception indicating it's stale

Scenario: already removed entity is passed

Given entities [1, 3 ,5]
When I get entity with value 3 and keep it somewhere for later usage
And I call an application service that removes it
And I call an application service that only uses it but never modifies the database
Then the system throws an exception indicating that it doesn't exist anymore