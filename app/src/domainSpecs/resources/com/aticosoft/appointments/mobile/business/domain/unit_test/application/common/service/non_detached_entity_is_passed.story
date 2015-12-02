Narrative:

As a developer
I want to make sure the entities passed to application services are always in detached state
So that they never mistakenly cause en new record to be created in the database for it

Scenario: a new entity is passed

When I create a new entity and pass it to an application service
Then the system throws an exception indicating it's not detached