Scenarios for simple actions (that is, actions that receive a single event at a time).

Notes:
 * The actions used by the scenarios of this story use multiple-times actions.

Scenario: event with no actions

When event NO_SUBSCRIPTIONS occurs
!-- No exception was thrown

Scenario: different event type occurs

When event FIVE_SUBSCRIPTIONS occurs
Then THREE_SUBSCRIPTIONS actions don't get triggered

Scenario: event is not modified by any action

Given THREE_SUBSCRIPTIONS actions don't modify the event
When event THREE_SUBSCRIPTIONS occurs with value 3
Then THREE_SUBSCRIPTIONS actions produce the next values in order:
[a1:3, a2:3, a3:3]
And there are no THREE_SUBSCRIPTIONS events left

Scenario: event is modified by some actions

Given the 2nd FIVE_SUBSCRIPTIONS action that gets executed increments its value 1 time
And the 3rd FIVE_SUBSCRIPTIONS action that gets executed increments its value 3 times
And the 4rd FIVE_SUBSCRIPTIONS action that gets executed increments its value 2 time
When event FIVE_SUBSCRIPTIONS occurs with value 3
Then FIVE_SUBSCRIPTIONS actions produce the next values in order:
[a1:3, a2:4, a1:4, a2:4, a3:5, a1:5, a2:5, a3:6, a1:6, a2:6, a3:6, a4:7, a1:7, a2:7, a3:7, a4:8, a1:8, a2:8, a3:8, a4:8, a5:8]
And there are no FIVE_SUBSCRIPTIONS events left

Scenario: some events occur before any action gets triggered

Given THREE_SUBSCRIPTIONS actions execution is suspended
And the 2nd THREE_SUBSCRIPTIONS action that gets executed increments its value 1 time
When event THREE_SUBSCRIPTIONS occurs with value 3
And event THREE_SUBSCRIPTIONS occurs with value 7
And event THREE_SUBSCRIPTIONS occurs with value 11
And THREE_SUBSCRIPTIONS actions execution is resumed
Then THREE_SUBSCRIPTIONS actions produce the next values in order:
[a1:3, a2:4, a1:4, a2:4, a3:4, a1:7, a2:8, a1:8, a2:8, a3:8, a1:11, a2:12, a1:12, a2:12, a3:12]