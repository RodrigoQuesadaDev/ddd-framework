Event is updated.

Lifecycle:
Before:
Given no events

Scenario: set-up

Given event FIVE_ACTIONS has 5 actions defined

Scenario: partially processed event is updated by action

Given the 4th FIVE_ACTIONS action that gets executed increments its value: 1 time
And after the 4th FIVE_ACTIONS action is executed, actions execution is suspended
When event FIVE_ACTIONS occurs with value 3
Then the only FIVE_ACTIONS events left are [4]
And FIVE_ACTIONS event has only been processed by the 4th action
And FIVE_ACTIONS event is not marked as keep

Scenario: partially processed kept event is updated by action

Given the 2nd FIVE_ACTIONS action that gets executed marks it to be kept 1 time
And the 4th FIVE_ACTIONS action that gets executed increments its value: 1 time
And after the 4th FIVE_ACTIONS action is executed, actions execution is suspended
When event FIVE_ACTIONS occurs with value 3
Then the only FIVE_ACTIONS events left are [4]
And FIVE_ACTIONS event has only been processed by the 4th action
And FIVE_ACTIONS event is not marked as keep

Scenario: partially processed event is updated by service

Given after the 4th FIVE_ACTIONS action is executed, actions execution is suspended
When event FIVE_ACTIONS occurs with value 3
And FIVE_ACTIONS event with value 3 is incremented by a service
Then the only FIVE_ACTIONS events left are [4]
And FIVE_ACTIONS event has not been processed by any action and is not marked as keep

Scenario: partially processed kept event is updated by service

Given the 2nd FIVE_ACTIONS action that gets executed marks it to be kept 1 time
And after the 4th FIVE_ACTIONS action is executed, actions execution is suspended
When event FIVE_ACTIONS occurs with value 3
And FIVE_ACTIONS event with value 3 is incremented by a service
Then the only FIVE_ACTIONS events left are [4]
And FIVE_ACTIONS event has not been processed by any action and is not marked as keep

Scenario: fully processed kept event is updated by service

Given the 2nd FIVE_ACTIONS action that gets executed marks it to be kept 1 time
And after the 5th FIVE_ACTIONS action is executed, actions execution is suspended
When event FIVE_ACTIONS occurs with value 3
And FIVE_ACTIONS event with value 3 is incremented by a service
Then the only FIVE_ACTIONS events left are [4]
And FIVE_ACTIONS event has not been processed by any action and is not marked as keep

Scenario: some of the multiple partially processed events queried by an action are updated

Given FIVE_ACTIONS actions execution is suspended
And the 4th FIVE_ACTIONS action that gets executed increments the value of odd unprocessed events: 1 time
And after the 4th FIVE_ACTIONS action is executed, actions execution is suspended
When event FIVE_ACTIONS occurs with value 3
And event FIVE_ACTIONS occurs with value 8
And event FIVE_ACTIONS occurs with value 11
And FIVE_ACTIONS actions execution is resumed
Then the only FIVE_ACTIONS events left are [4, 8, 12]
And FIVE_ACTIONS event with value 8 have only been processed by actions [1, 2, 3, 4]
And FIVE_ACTIONS events with values [4, 12] have only been processed by the 4th action
And FIVE_ACTIONS events are not marked as keep

Scenario: some of the multiple partially processed kept events queried by an action are updated

Given FIVE_ACTIONS actions execution is suspended
And the 2nd FIVE_ACTIONS action that gets executed marks all unprocessed events to be kept: 1 time
And the 4th FIVE_ACTIONS action that gets executed increments the value of odd unprocessed events: 1 time
And after the 4th FIVE_ACTIONS action is executed, actions execution is suspended
When event FIVE_ACTIONS occurs with value 3
And event FIVE_ACTIONS occurs with value 8
And event FIVE_ACTIONS occurs with value 11
And FIVE_ACTIONS actions execution is resumed
Then the only FIVE_ACTIONS events left are [4, 8, 12]
And FIVE_ACTIONS event with value 8 have only been processed by actions [1, 2, 3, 4]
And FIVE_ACTIONS event with value 8 is marked as keep only by the 2nd action
And FIVE_ACTIONS events with values [4, 12] have only been processed by the 4th action
And FIVE_ACTIONS events with values [4, 12] are not marked as keep