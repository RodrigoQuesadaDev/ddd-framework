Event is marked to be kept.

Lifecycle:
Before:
Given no events

Scenario: set-up

Given event FIVE_ACTIONS has 5 actions defined

Scenario: event is marked to be kept

Given the 2nd FIVE_ACTIONS action that gets executed marks it to be kept 1 time
And the 4th FIVE_ACTIONS action that gets executed marks it to be kept 1 time
When event FIVE_ACTIONS occurs with value 3
Then the only FIVE_ACTIONS events left are [3]
And FIVE_ACTIONS event has been processed by all actions
And FIVE_ACTIONS event is marked as keep only by actions [2, 4]

Scenario: event is simultaneously updated and marked to be kept

Given the 2nd FIVE_ACTIONS action that gets executed marks it to be kept 1 time
And the 4th FIVE_ACTIONS action that gets executed increments its value: 1 time
And the 4th FIVE_ACTIONS action that gets executed marks it to be kept 1 time
When event FIVE_ACTIONS occurs with value 3
Then the only FIVE_ACTIONS events left are [3]
And FIVE_ACTIONS event has been processed by all actions
And FIVE_ACTIONS event is marked as keep only by the 4th action

Scenario: event is updated and then marked to be kept

Given the 2nd FIVE_ACTIONS action that gets executed increments its value: 1 time
And the 4th FIVE_ACTIONS action that gets executed marks it to be kept 1 time
When event FIVE_ACTIONS occurs with value 3
Then the only FIVE_ACTIONS events left are [3]
And FIVE_ACTIONS events have been processed by all actions
And FIVE_ACTIONS event is marked as keep only by the 4th action

Scenario: multiple events queried by an action are all marked to be kept using keepAll method

Given FIVE_ACTIONS actions execution is suspended
And the 2nd FIVE_ACTIONS action that gets executed marks it to be kept 1 time
And the 4th FIVE_ACTIONS action that gets executed marks all unprocessed events to be kept: 1 time
When event FIVE_ACTIONS occurs with value 3
And event FIVE_ACTIONS occurs with value 7
And event FIVE_ACTIONS occurs with value 11
And FIVE_ACTIONS actions execution is resumed
Then the only FIVE_ACTIONS events left are [3, 7, 11]
And FIVE_ACTIONS events have been processed by all actions
And FIVE_ACTIONS event with value 3 is marked as keep only by actions [2, 4]
And FIVE_ACTIONS events with values [7, 11] are marked as keep only by the 4th action

Scenario: multiple events queried by an action are all simultaneously updated and marked to be kept using keepAll method

Given FIVE_ACTIONS actions execution is suspended
And the 2nd FIVE_ACTIONS action that gets executed marks it to be kept 1 time
And the 4th FIVE_ACTIONS action that gets executed increments the value of unprocessed events: 1 time
And the 4th FIVE_ACTIONS action that gets executed marks all unprocessed events to be kept: 1 time
When event FIVE_ACTIONS occurs with value 3
And event FIVE_ACTIONS occurs with value 7
And event FIVE_ACTIONS occurs with value 11
And FIVE_ACTIONS actions execution is resumed
Then the only FIVE_ACTIONS events left are [4, 8, 12]
And FIVE_ACTIONS events have been processed by all actions
And FIVE_ACTIONS events are marked as keep only by the 4th action

Scenario: multiple events queried by an action are all updated and then marked to be kept using keepAll method

Given FIVE_ACTIONS actions execution is suspended
And the 2nd FIVE_ACTIONS action that gets executed increments the value of unprocessed events: 1 time
And the 4th FIVE_ACTIONS action that gets executed marks all unprocessed events to be kept: 1 time
When event FIVE_ACTIONS occurs with value 3
And event FIVE_ACTIONS occurs with value 7
And event FIVE_ACTIONS occurs with value 11
And FIVE_ACTIONS actions execution is resumed
Then the only FIVE_ACTIONS events left are [4, 8, 12]
And FIVE_ACTIONS events have been processed by all actions
And FIVE_ACTIONS events are marked as keep only by the 4th action

Scenario: some of the multiple events queried by an action are marked to be kept using keep method

Given FIVE_ACTIONS actions execution is suspended
And the 2nd FIVE_ACTIONS action that gets executed marks it to be kept 1 time
And the 4th FIVE_ACTIONS action that gets executed marks odd unprocessed events to be kept: 1 time
When event FIVE_ACTIONS occurs with value 3
And event FIVE_ACTIONS occurs with value 8
And event FIVE_ACTIONS occurs with value 11
And FIVE_ACTIONS actions execution is resumed
Then the only FIVE_ACTIONS events left are [3, 11]
And FIVE_ACTIONS events have been processed by all actions
And FIVE_ACTIONS event with value 3 is marked as keep only by actions [2, 4]
And FIVE_ACTIONS event with value 11 is marked as keep only by the 4th action

Scenario: some of the multiple events queried by an action are simultaneously updated and marked to be kept using keep method

Given FIVE_ACTIONS actions execution is suspended
And the 2nd FIVE_ACTIONS action that gets executed marks it to be kept 1 time
And the 4th FIVE_ACTIONS action that gets executed increments the value of unprocessed events: 1 time
And the 4th FIVE_ACTIONS action that gets executed marks odd unprocessed events to be kept: 1 time
When event FIVE_ACTIONS occurs with value 3
And event FIVE_ACTIONS occurs with value 8
And event FIVE_ACTIONS occurs with value 11
And FIVE_ACTIONS actions execution is resumed
Then the only FIVE_ACTIONS events left are [4, 12]
And FIVE_ACTIONS events have been processed by all actions
And FIVE_ACTIONS events are marked as keep only by the 4th action

Scenario: some of the multiple events queried by an action are updated and then marked to be kept using keep method

Given FIVE_ACTIONS actions execution is suspended
And the 2nd FIVE_ACTIONS action that gets executed increments the value of unprocessed events: 1 time
And the 4th FIVE_ACTIONS action that gets executed marks odd unprocessed events to be kept: 1 time
When event FIVE_ACTIONS occurs with value 3
And event FIVE_ACTIONS occurs with value 8
And event FIVE_ACTIONS occurs with value 11
And FIVE_ACTIONS actions execution is resumed
Then the only FIVE_ACTIONS events left are [4, 12]
And FIVE_ACTIONS events have been processed by all actions
And FIVE_ACTIONS events are marked as keep only by the 4th action