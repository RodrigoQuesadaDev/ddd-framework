Narrative:

In order to observe the total count of entities of a given type
As a developer
I want to be able to subscribe to an EntityObserver

Scenario: scenario 1

Given no data
And I'm observing the total count of entities
When later I insert [1, 2, 3]
And later I insert [10, 11, 12, 13, 14]
And later I insert [22]
And later I insert [23, 24]
And later I insert [25, 26, 27]
And later I change 14 into 20
And later I change 12 into 21
And later I delete [11, 13]
And later I delete [26]
And later I delete [27, 1]
And later I change 2 into 4
And later I change 3 into 5
Then later the total count values observed were [0, 3, 8, 9, 11, 14, 12, 11, 9]