
Scenario: test the initial interval duration

Given I subscribe to an observable using throttleLast with initialIntervalDuration=0.25s and intervalDuration=0.5s
When after 0 secs the source observable emits [1, 2, 3, 4, 5]
Then after 0 secs emitted values are []
Then after 0.24 secs emitted values are []
Then after 0.01 secs emitted values are [5]

Scenario: test the rest of interval durations

Given I subscribe to an observable using throttleLast with initialIntervalDuration=0.25s and intervalDuration=0.5s
When after 0 secs the source observable emits [1, 2, 3, 4, 5]
When after 0.25 secs the source observable emits [6, 7, 8, 9, 10]
And after 0.5 secs the source observable emits [11, 12, 13, 14, 15]
And after 0.5 secs the source observable emits [16, 17, 18, 19, 20]
And after 0.5 secs the source observable emits [21, 22, 23, 24, 25]
Then after 0 secs emitted values are [5, 10, 15, 20]
And after 0.49 secs emitted values are [5, 10, 15, 20]
And after 0.01 secs emitted values are [5, 10, 15, 20, 25]
