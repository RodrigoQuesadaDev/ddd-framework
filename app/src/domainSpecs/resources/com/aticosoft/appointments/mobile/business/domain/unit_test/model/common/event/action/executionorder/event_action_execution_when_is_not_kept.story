Event action execution behavior when event is not marked to be kept.

Notes:
 * Single-event actions used by the scenarios of this story use actions with FIFO queries and process 1 event at a time.

Scenario: set-up

Given event THREE_ACTIONS has 3 actions defined
Given event FIVE_ACTIONS has 5 actions defined

Scenario: event is not modified by any action

Given THREE_ACTIONS actions don't modify the event
When event THREE_ACTIONS occurs with value 3
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:3, a3:3]
And there are no THREE_ACTIONS events left

Scenario: event is modified by one action

Given the 2nd THREE_ACTIONS action that gets executed increments its value: 10 times
When event THREE_ACTIONS occurs with value 3
And event THREE_ACTIONS occurs with value 7
And event THREE_ACTIONS occurs with value 11
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:4, a3:4, a1:4, a2:8, a3:8, a1:8, a2:12, a3:12, a1:12]
And there are no THREE_ACTIONS events left

Scenario: event is modified by some actions

Given the 2nd FIVE_ACTIONS action that gets executed increments its value: 1 time
And the 3rd FIVE_ACTIONS action that gets executed increments its value: 3 times
And the 4rd FIVE_ACTIONS action that gets executed increments its value: 2 times
When event FIVE_ACTIONS occurs with value 3
Then FIVE_ACTIONS actions produced the next values in order:
[a1:3, a2:4, a3:5, a4:6, a5:6, a1:6, a2:6, a3:7, a4:8, a5:8, a1:8, a2:8, a3:9, a4:9, a5:9, a1:9, a2:9]
And there are no FIVE_ACTIONS events left

Scenario: some events occur before any action gets triggered

Given THREE_ACTIONS actions execution is suspended
And the 2nd THREE_ACTIONS action that gets executed increments its value: 1 time
When event THREE_ACTIONS occurs with value 3
And event THREE_ACTIONS occurs with value 7
And event THREE_ACTIONS occurs with value 11
And THREE_ACTIONS actions execution is resumed
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:4, a3:4, a1:4, a2:8, a3:8, a1:8, a2:12, a3:12, a1:12]
And there are no THREE_ACTIONS events left