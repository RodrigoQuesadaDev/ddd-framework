Scenarios for simple actions (that is, actions that receive a single event at a time).

Notes:
 * The actions used by the scenarios of this story use actions with FIFO queries and process 1 event at a time.

Scenario: event with no actions

Given event NO_ACTIONS has 0 actions defined
When event NO_ACTIONS occurs
!-- No exception was thrown

Scenario: different event type occurs

Given event FIVE_ACTIONS has 5 actions defined
When event FIVE_ACTIONS occurs
Then THREE_ACTIONS actions don't get triggered

Scenario: event is not modified by any action

Given event THREE_ACTIONS has 3 actions defined
Given THREE_ACTIONS actions don't modify the event
When event THREE_ACTIONS occurs with value 3
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:3, a3:3]
And there are no THREE_ACTIONS events left

Scenario: event is modified by some actions

Given the 2nd FIVE_ACTIONS action that gets executed increments its value: 1 time
And the 3rd FIVE_ACTIONS action that gets executed increments its value: 3 times
And the 4rd FIVE_ACTIONS action that gets executed increments its value: 2 times
When event FIVE_ACTIONS occurs with value 3
Then FIVE_ACTIONS actions produced the next values in order:
[a1:3, a2:4, a1:4, a2:4, a3:5, a1:5, a2:5, a3:6, a1:6, a2:6, a3:7, a1:7, a2:7, a3:7, a4:8, a1:8, a2:8, a3:8, a4:9, a1:9, a2:9, a3:9, a4:9, a5:9]
And there are no FIVE_ACTIONS events left

Scenario: some events occur before any action gets triggered

Given THREE_ACTIONS actions execution is suspended
And the 2nd THREE_ACTIONS action that gets executed increments its value: 1 time
When event THREE_ACTIONS occurs with value 3
And event THREE_ACTIONS occurs with value 7
And event THREE_ACTIONS occurs with value 11
And THREE_ACTIONS actions execution is resumed
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:4, a1:4, a2:4, a3:4, a1:7, a2:8, a1:8, a2:8, a3:8, a1:11, a2:12, a1:12, a2:12, a3:12]

Scenario: event is kept

Given the 2nd THREE_ACTIONS action that gets executed marks the event with an initial value of 7 to be kept 3 times
When event THREE_ACTIONS occurs with value 3
And event THREE_ACTIONS occurs with value 7
And event THREE_ACTIONS occurs with value 11
And event with value 7 is incremented by a service
And event THREE_ACTIONS occurs with value 13
And event with value 8 is incremented by a service
And event THREE_ACTIONS occurs with value 17
And event with value 9 is incremented by a service
And event THREE_ACTIONS occurs with value 19
Then THREE_ACTIONS actions produced the next values in order:
[a1:3, a2:3, a3:3, a1:7, a2:7, a3:7, a1:11, a2:11, a3:11, a1:8, a2:8, a3:8, a1:13, a2:13, a3:13, a1:9, a2:9, a3:9, a1:17, a2:17, a3:17, a1:10, a2:10, a3:10, a1:19, a2:19, a3:19]
And there are no THREE_ACTIONS events left
