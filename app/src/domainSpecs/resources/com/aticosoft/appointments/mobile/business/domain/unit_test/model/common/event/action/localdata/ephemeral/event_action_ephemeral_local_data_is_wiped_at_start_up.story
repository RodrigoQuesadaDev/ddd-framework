Event action ephemeral local data is wiped out when the app starts.

Scenario: app starts-up

!-- Given app starts-up
Given an EventActionEphemeralLocalDataManager instance's wipeData method has not been executed when is first created
Then the EventActionEphemeralLocalDataManager's wipeData method has been executed

Scenario: wipeData is executed

Given there are a total of 10 ephemeral local data entries for different events
And the EventActionEphemeralLocalDataManager's wipeData method has not been executed
When the EventActionEphemeralLocalDataManager's wipeData method is executed
Then there are no ephemeral local data entries left for any event
And the EventActionEphemeralLocalDataManager's wipeData method has been executed