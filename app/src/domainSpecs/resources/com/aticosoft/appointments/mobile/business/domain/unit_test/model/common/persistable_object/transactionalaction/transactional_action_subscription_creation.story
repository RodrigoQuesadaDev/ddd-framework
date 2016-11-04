Transactional action subscription creation.

Scenario: no subscriptions

Given no declared transactional actions for NO_SUBSCRIPTIONS object changes
Then no transactional actions are subscribed to NO_SUBSCRIPTIONS object changes

Scenario: 1 subscription

Given ONE_SUBSCRIPTION transactional actions with the next ids are declared: [1]
Then only 1 transactional action is subscribed to ONE_SUBSCRIPTION object changes and it has the id 1

Scenario: multiple subscriptions

Given FIVE_SUBSCRIPTIONS transactional actions with the next ids are declared: [1, 2, 3, 4, 5]
Then only 5 transactional actions are subscribed to FIVE_SUBSCRIPTIONS object changes and they have the ids [1, 2, 3, 4, 5]