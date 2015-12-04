Narrative:

As a developer
I want to automatically perform checking on the definition of entities for Command classes
So that I can test their correct implementation

!-- TODO check it's one of the supported iterable types
!-- TODO how to test a Pair (or any other non-compliant container) is not used for passing entities? Perhaps check parameterized types info?

Scenario: normal, inheritance (field on parent),

Given a system state
When I do something
Then system is in a different state