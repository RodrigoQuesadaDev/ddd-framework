Narrative:

In order to have a better app performance when displaying data
As a developer
I want to throttle the data refresh rate

Scenario: scenario 1

Given no data
And a data refresh rate time of 0.5 secs with an initial offset of <offsetTime>
And I'm displaying the number of rows inserted

!-- First window throttling
When after 0 secs I insert [1, 2, 3, 4, 5]
Then after <justBeforeOffsetTime> the values that have been displayed are [0]
Then after 0.01 secs the values that have been displayed are [0, 5]

!-- Throttling for next windows
When after 0 secs I insert [6, 7, 8, 9, 10]
And after 0.5 secs I insert [11, 12, 13, 14, 15]
And after 0.5 secs I insert [16, 17, 18, 19, 20]
Then after 0 secs the values that have been displayed are [0, 5, 10, 15]
Then after 0.49 secs the values that have been displayed are [0, 5, 10, 15]
Then after 0.01 secs the values that have been displayed are [0, 5, 10, 15, 20]

!-- No data is emitted during a some time
Then after 10 secs the values that have been displayed are [0, 5, 10, 15, 20]

!-- Throttling for next windows
When after 0 secs I insert [21, 22, 23, 24, 25]
Then after 0 secs the values that have been displayed are [0, 5, 10, 15, 20]
Then after 0.5 secs the values that have been displayed are [0, 5, 10, 15, 20, 25]

Examples:
| offsetTime | justBeforeOffsetTime |
| 0.25 secs  | 0.24 secs            |
| 0.5 secs   | 0.49 secs            |