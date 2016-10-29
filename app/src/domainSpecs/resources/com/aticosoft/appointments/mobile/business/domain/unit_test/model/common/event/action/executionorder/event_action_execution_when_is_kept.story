Event action execution behavior when event is kept.

Notes:
 * Single-event actions used by the scenarios of this story use actions with FIFO queries and process 1 event at a time.

Scenario: set-up

Given event THREE_ACTIONS has 3 actions defined
Given event FIVE_ACTIONS has 5 actions defined

Scenario: one event is marked as kept, others are not

Given the 2nd THREE_ACTIONS action that gets executed marks the event with an initial value of 7 to be kept 3 times
When event THREE_ACTIONS occurs with value 3
And event THREE_ACTIONS occurs with value 7
And event THREE_ACTIONS occurs with value 11
And THREE_ACTIONS event with value 7 is incremented by a service
And event THREE_ACTIONS occurs with value 13
And THREE_ACTIONS event with value 8 is incremented by a service
And event THREE_ACTIONS occurs with value 17
And THREE_ACTIONS event with value 9 is incremented by a service
And event THREE_ACTIONS occurs with value 19
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:3, a3:3, a1:7, a2:7, a3:7, a1:11, a2:11, a3:11, a1:8, a2:8, a3:8, a1:13, a2:13, a3:13, a1:9, a2:9, a3:9, a1:17, a2:17, a3:17, a1:10, a2:10, a3:10, a1:19, a2:19, a3:19]
And there are no THREE_ACTIONS events left

Scenario: event is updated by action and marked as kept

Given the 2nd THREE_ACTIONS action that gets executed marks events to be kept: 3 times
And the 2nd THREE_ACTIONS action that gets executed increments its value: 10 times
When event THREE_ACTIONS occurs with value 3
And THREE_ACTIONS event with value 4 is incremented by a service
And THREE_ACTIONS event with value 6 is incremented by a service
And THREE_ACTIONS event with value 8 is incremented by a service
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:4, a3:4, a1:4, a2:6, a3:6, a1:6, a2:8, a3:8, a1:8, a2:10, a3:10, a1:10]
And there are no THREE_ACTIONS events left

Scenario: event is marked as kept after updating it

Given the 2nd THREE_ACTIONS action that gets executed increments its value: 10 times
And the 3rd THREE_ACTIONS action that gets executed marks the event with an initial value of 7 to be kept 3 times
When event THREE_ACTIONS occurs with value 3
And THREE_ACTIONS event with value 4 is incremented by a service
And THREE_ACTIONS event with value 6 is incremented by a service
And THREE_ACTIONS event with value 8 is incremented by a service
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:4, a3:4, a1:4, a2:6, a3:6, a1:6, a2:8, a3:8, a1:8, a2:10, a3:10, a1:10]
And there are no THREE_ACTIONS events left

Scenario: event is updated by action after is kept

Given the 2nd THREE_ACTIONS action that gets executed marks the event with an initial value of 7 to be kept 3 times
And the 3rd THREE_ACTIONS action that gets executed increments its value: 10 times
When event THREE_ACTIONS occurs with value 3
And THREE_ACTIONS event with value 4 is incremented by a service
And THREE_ACTIONS event with value 6 is incremented by a service
And THREE_ACTIONS event with value 8 is incremented by a service
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:3, a3:4, a1:4, a2:4, a3:6, a1:6, a2:6, a3:8, a1:8, a2:8, a3:10, a1:10, a2:10]
And there are no THREE_ACTIONS events left

Scenario: multiple events are updated by action simultaneously

Given THREE_ACTIONS actions execution is suspended
And the 2nd THREE_ACTIONS action that gets executed increments the value of unprocessed events: 10 times
And the 2nd THREE_ACTIONS action outputs the average of unprocessed events after processing them
When event THREE_ACTIONS occurs with value 3
And event THREE_ACTIONS occurs with value 7
And event THREE_ACTIONS occurs with value 11
And THREE_ACTIONS actions execution is resumed
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:8, a3:4, a1:4, a2:10, a3:8, a1:8, a2:12, a3:12, a1:12]
And there are no THREE_ACTIONS events left

Scenario: multiple events queried by an action are all marked to be kept using keepAll method

Given THREE_ACTIONS actions execution is suspended
And the 2nd THREE_ACTIONS action that gets executed increments the value of unprocessed events: 10 times
And the 2nd THREE_ACTIONS action outputs the average of unprocessed events after processing them
And the 2nd THREE_ACTIONS action that gets executed marks all unprocessed events to be kept: 3 times
When event THREE_ACTIONS occurs with value 3
And event THREE_ACTIONS occurs with value 7
And event THREE_ACTIONS occurs with value 11
And THREE_ACTIONS actions execution is resumed
And THREE_ACTIONS events with values [8, 12] are incremented by a service
And THREE_ACTIONS events with values [4, 9] are incremented by a service
And THREE_ACTIONS events with values [5, 10, 13] are incremented by a service
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:8, a3:4, a1:4, a2:0, a3:8, a1:8, a2:0, a3:12, a1:12,
!-- 4, 8, 12
a2:11, a3:9, a1:9, a2:0, a3:13, a1:13,
!-- 4, 9, 13
a2:7.5, a3:5, a1:5, a2:0, a3:10, a1:10,
!-- 5, 10, 13
a2:10.33, a3:6, a1:6, a2:0, a3:11, a1:11, a2:0, a3:14, a1:14]
And there are no THREE_ACTIONS events left

Scenario: some of the multiple events queried by an action are marked to be kept using keep method

Given THREE_ACTIONS actions execution is suspended
And the 2nd THREE_ACTIONS action that gets executed increments the value of unprocessed events: 10 times
And the 2nd THREE_ACTIONS action outputs the average of unprocessed events after processing them
And the 2nd THREE_ACTIONS action that gets executed marks odd unprocessed events to be kept: 3 times
When event THREE_ACTIONS occurs with value 3
And event THREE_ACTIONS occurs with value 8
And event THREE_ACTIONS occurs with value 11
And THREE_ACTIONS actions execution is resumed
And THREE_ACTIONS events with values [4, 12] is incremented by a service
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:8.33, a3:4, a1:4, a2:0, a3:9, a1:9, a2:0, a3:12, a1:12,
!-- 4, 12
a2:9, a3:5, a1:5, a2:0, a3:13, a1:13]
And there are no THREE_ACTIONS events left