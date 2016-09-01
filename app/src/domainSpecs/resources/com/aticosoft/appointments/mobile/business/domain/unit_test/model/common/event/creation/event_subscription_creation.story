Event subscription creation.

Scenario: no subscriptions

Given no declared actions for NO_SUBSCRIPTIONS event
Then no actions are subscribed to NO_SUBSCRIPTIONS event

Scenario: 1 subscription

Given ONE_SUBSCRIPTION event actions with the next ids are declared: [1]
Then only 1 action is subscribed to ONE_SUBSCRIPTION event and it has the id 1

Scenario: multiple subscriptions

Given FIVE_SUBSCRIPTIONS event actions with the next ids are declared: [1, 2, 3, 4, 5]
Then only 5 actions are subscribed to FIVE_SUBSCRIPTIONS event and they have the ids [1, 2, 3, 4, 5]