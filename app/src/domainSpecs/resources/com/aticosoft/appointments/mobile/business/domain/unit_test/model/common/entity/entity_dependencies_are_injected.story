Narrative:

As a developer
I want to be able to define dependencies on entity classes
So that I can have them automatically injected by the system when creating or loading them

Scenario: entity is created

Given a new entity
Then the dependency was set

Scenario: entity is loaded from store for application service

Given entities [3]
When I clear the datastore cache
And I call an application service that makes use of an injected dependency for entity with value 3
!-- No exception was thrown