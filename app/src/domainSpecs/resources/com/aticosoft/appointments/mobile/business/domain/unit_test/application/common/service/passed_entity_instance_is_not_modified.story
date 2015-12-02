Narrative:

As a developer
I want to make sure the system only modifies copies of the entity instances passed
So that the instances used on the presentation layer are never modified/dirty

Scenario: scenario 1

Given entities [1, 3 ,5]
When I get entity with value 3 and keep it somewhere for later usage
And I pass the kept instance to an application service that modifies it
Then the instance was never modified by the called service