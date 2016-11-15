Transactional action filtering.

Lifecycle:
Before:
Given no data
And transactional actions configuration is reset

Scenario: set-up

!-- id = ID,
Given 1 transactional action defined for S1 object
And 2 transactional actions defined for S2 object like this: [id1:p100, id2:p90, id3:p80, id4:p70, id5:p60]

Scenario: filtering prime values

Given S1 transactional action filters prime values
When I insert S1 objects [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
Then the values observed by the S1 transactional action were exactly (and in the same order): [2, 3, 7]

Scenario: finishes until no more changes are performed

Given S2 transactional action with ID 1 filters and increments values equal to 5
And S2 transactional action with ID 2 filters and increments values equal to 2
And S2 transactional action with ID 3 filters and increments values equal to 3
And S2 transactional action with ID 4 filters and increments values equal to 4
And S2 transactional action with ID 5 filters and increments values equal to 1
When I insert S2 objects [1]
Then S2 repository contains only: 6
And the values observed by the S2 transactional actions were exactly (and in the same order): [1, 2, 3, 4, 5]

Scenario: finishes until no more changes are performed; some actions don't even process the values

Given S2 transactional action with ID 1 filters no values
And S2 transactional action with ID 2 filters and increments values equal to 2
And S2 transactional action with ID 3 filters no values
And S2 transactional action with ID 4 filters and increments values equal to 1
And S2 transactional action with ID 5 filters no values
When I insert S2 objects [1]
Then S2 repository contains only: 3
And the values observed by the S2 transactional actions were exactly (and in the same order): [1, 2]