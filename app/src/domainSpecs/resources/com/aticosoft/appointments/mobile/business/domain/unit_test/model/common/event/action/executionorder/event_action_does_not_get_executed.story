Event action doesn't get executed.

Scenario: event with no actions

Given event NO_ACTIONS has 0 actions defined
When event NO_ACTIONS occurs
!-- No exception was thrown

Scenario: different event type occurs

Given event FIVE_ACTIONS has 5 actions defined
And event THREE_ACTIONS has 3 actions defined
When event FIVE_ACTIONS occurs
Then THREE_ACTIONS actions don't get triggered