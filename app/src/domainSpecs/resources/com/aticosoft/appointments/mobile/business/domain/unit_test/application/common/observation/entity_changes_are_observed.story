Narrative:

As a developer
I want to be able to be able to subscribe to an EntityCallbakListener
So that I can observe the changes occurring to entities of a given type

Scenario: scenario description

Given no data
And I subscribe to the EntityCallbackListener
When I insert [1, 2, 3, 4, 5]
And after that I insert [6, 7, 8, 9, 10]
And after that I delete [2, 4, 6, 8]
And after that I change 1 into 2
And after that I change 7 into 8
Then the entity changes observed were [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 2, 4, 6, 8, 2, 8]