Narrative:

In order to clean up ThreadLocal values automatically
As a developer
I want to be able to be able to register various ThreadLocal instances with a given object that should trigger that action

!-- TODO change after this stuff is fixed: http://jbehave.org/reference/latest/given-stories.html (not working!!!)
Meta: @threadLocalCleanerType UNSAFE

Scenario: a clean-up object is created

Given a <threadLocalCleanerType> ThreadLocalCleaner object is created

Scenario: ThreadLocal instances are created

Given a ThreadLocalDelegate that is initialized with a value of 0 and is registered with the ThreadLocalCleaner object
When the value of the delegated property is set to <value>

Examples:
| value |
| 1     |
| 2     |
| 3     |
| 4     |
| 5     |
| 6     |
| 7     |
| 8     |
| 9     |
| 10    |

Scenario: number of registered ThreadLocal objects is checked

Then the number of registered ThreadLocal objects is 10

Scenario: instances are cleaned-up

When the cleanUpThreadLocalInstances method of the ThreadLocalCleaner object is called
Then the registered ThreadLocal objects were clean-up