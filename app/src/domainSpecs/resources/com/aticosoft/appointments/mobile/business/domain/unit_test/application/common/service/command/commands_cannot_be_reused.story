Narrative:

As a developer
I want to prevent command objects from being reused
So that I can avoid having concurrency-related issues and other problems with them

Scenario: command is used twice

Given I create a command object and pass it to an application service
And the next step might throw an exception
When I reuse it a second time on another call
Then the system throws an exception indicating the command has already being used

Scenario: command and nested command are used

Given I create a command object and pass it to an application service
And the next step might throw an exception
When I reuse one of its nested commands on a different call
Then the system throws an exception indicating the command has already being used