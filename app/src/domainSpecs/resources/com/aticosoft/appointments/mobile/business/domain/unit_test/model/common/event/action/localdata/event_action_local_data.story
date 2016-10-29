Parameterized event action local data story.

GivenStories:

Scenario: main scenario

!-- a = Action, v = Value, c = Count
Given event SAMPLE has 4 actions defined
And event uses <localDataType> local data when processing events
And SAMPLE actions always mark events to be kept
And the 1st SAMPLE action that gets executed counts how many times an event has been even
And the 2nd SAMPLE action that gets executed counts how many times an event has been prime
And the 3rd SAMPLE action that gets executed counts how many times an event has been processed by it
And the 4th SAMPLE action that gets executed counts how many times an event has been odd
When event SAMPLE occurs with value 3
And event SAMPLE occurs with value 11
And event SAMPLE occurs with value 23
And SAMPLE event with value 11 is incremented by a service
And SAMPLE event with value 23 is incremented by a service
And SAMPLE event with value 12 is incremented by a service
And SAMPLE event with value 3 is incremented by a service
And SAMPLE event with value 13 is incremented by a service
And SAMPLE event with value 4 is incremented by a service
And SAMPLE event with value 24 is incremented by a service
And SAMPLE event with value 14 is incremented by a service
And SAMPLE event with value 15 is incremented by a service
And SAMPLE event with value 25 is incremented by a service
Then SAMPLE action produces the next values:
[a1:c1:v4,
a1:c1:v12, a1:c2:v14, a1:c3:v16,
a1:c1:v24, a1:c2:v26,

a2:c1:v3,
a2:c1:v11, a2:c2:v13,
a2:c1:v23,

a3:c1:v3, a3:c2:v4, a3:c3:v5,
a3:c1:v11, a3:c2:v12, a3:c3:v13, a3:c4:v14, a3:c5:v15, a3:c6:v16
a3:c1:v23, a3:c2:v24, a3:c3:v25, a3:c4:v26,

a4:c1:v3, a4:c2:v5,
a4:c1:v11, a4:c2:v13, a4:c3:v15,
a4:c1:v23, a4:c2:v25]

Scenario: events are removed

Given there are a total of 12 <localDataType> local data entries for SAMPLE events
And there are 3 existing SAMPLE events
And SAMPLE actions don't mark events to be kept
When all SAMPLE events are incremented by a service
Then there are no <localDataType> local data entries left for SAMPLE events