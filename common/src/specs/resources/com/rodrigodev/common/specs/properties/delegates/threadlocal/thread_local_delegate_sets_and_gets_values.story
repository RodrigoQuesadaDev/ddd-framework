Narrative:

As a developer
I want to use an ThreadLocalDelegate
So that I can delegate properties to ThreadLocal instances

Scenario: initialization

Given a ThreadLocalDelegate that is initialized with a value of 13
Then the value of the delegated property is 13

Scenario: set a value

When the value of the delegated property is set to 17
Then the value of the delegated property is 17
