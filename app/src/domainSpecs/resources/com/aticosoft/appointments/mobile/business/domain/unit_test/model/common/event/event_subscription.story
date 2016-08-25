Event subscription.

Meta: @subsNumber 1

Scenario: non-subscribed event

!-- right now there is no subscriptions
When event A occurs
Then no exception is thrown

Scenario: different event type gets subscribed

Given event A gets subscribed <subsNumber> times
When event B occurs
Then actions subscribed to event A don't get triggered

Scenario: event is not updated by an action

Given the actions subscribed to event A don't update it
When event A occurs
Then only 1 of the actions subscribed to event A gets triggered, and only once
And that event A got removed

Scenario: event is updated by an action

Given the actions subscribed to event A increment its value each time they are executed except when the value is equal or greater than 5
When event A occurs with value 1

Then if only 1 action is subscribed to event A, it gets triggered 5 times

Then if more than 1 action is subscribed to event A, they get triggered 5 times in total and the individual amount of times they get triggered doesn't differ from the others by more than 1

Then actions subscribed to event A get triggered with the values [1, 2, 3, 4, 5], in that order
And that event A got removed

Scenario: event is kept during some executions

Given the actions subscribed to event A marks it to be kept during 5 executions
When event A occurs with value 1
Then actions subscribed to event A get triggered <subsNumber>*5 times with the value 1
And that event A got removed

Scenario: execution order

Given no actions subscribed

Given the actions subscribed to event A increment its value each time they are executed except when the value is equal or greater than 5
And event A occurs with value 1

Given the actions that subscribe to event A don't update it
And event A occurs 5 times with the values [11, 13, 17, 19, 23]

When event A gets subscribed <subsNumber> times
Then if only 1 action is subscribed to event A, it gets triggered 5 times
And if more than 1 action is subscribed to event A, they get triggered 5 times in total
And actions subscribed to event A get triggered with the values [1, 2, 3, 4, 5, 11, 13, 17, 19, 23], in that order