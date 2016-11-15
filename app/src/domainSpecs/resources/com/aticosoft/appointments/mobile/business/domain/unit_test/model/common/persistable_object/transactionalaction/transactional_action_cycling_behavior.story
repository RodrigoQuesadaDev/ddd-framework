Transactional action behavior on situations that might cause cycling issues.

Lifecycle:
Before:
Given no data
And the maximum value all transactional actions process is 10
And transactional actions configuration is reset

Scenario: interdependent actions of the same type

Given 2 transactional actions defined for S1 object like this: [id1, id2]
And the S1 transactional action with ID 1 increments odd values
And the S1 transactional action with ID 2 increments even values
When I insert S1 objects [3]
Then S1 repository contains only: 11
And the values observed by the S1 transactional action with ID 1 were exactly (and in the same order): [3, 5, 7, 9]
And the values observed by the S1 transactional action with ID 2 were exactly (and in the same order): [4, 6, 8, 10]

Scenario: action modifies the same object

Given 1 transactional action defined for S2 object
And the S2 transactional action increments values
When I insert S2 objects [3]
Then S2 repository contains only: 11
And the values observed by the S2 transactional action were exactly (and in the same order): [3, 4, 5, 6, 7, 8, 9, 10]

Scenario: chaining of actions A->B->C->A

Given the maximum value all transactional actions process is 30
And S3_B repository contains: 11
And S3_C repository contains: 23
And 1 transactional action defined for S3_A object
And the S3_A transactional action increments values of S3_B objects
And 1 transactional action defined for S3_B object
And the S3_B transactional action increments values of S3_C objects
And 1 transactional action defined for S3_C object
And the S3_C transactional action increments values of S3_A objects
When I insert S3_A objects [3]
Then S3_A repository contains only: 10
And S3_B repository contains only: 19
And S3_C repository contains only: 31
And the values observed by the S3_A transactional action were exactly (and in the same order): [3, 4, 5, 6, 7, 8, 9, 10]
And the values observed by the S3_B transactional action were exactly (and in the same order): [12, 13, 14, 15, 16, 17, 18, 19]
And the values observed by the S3_C transactional action were exactly (and in the same order): [24, 25, 26, 27, 28, 29, 30]

Scenario: chaining of actions A1,A2->B->C->A*,A3

Given the maximum value all transactional actions process is 30
And S4_B repository contains: 11
And S4_C repository contains: 23
And 3 transactional actions defined for S4_A object like this: [id1:p100, id2:p90, id3:p80]
And the S4_A transactional action with ID 2 increments values of S4_B objects
And 1 transactional action defined for S4_B object
And the S4_B transactional action increments values of S4_C objects
And 1 transactional action defined for S4_C object
And the S4_C transactional action increments values of S4_A objects
When I insert S4_A objects [3]
Then S4_A repository contains only: 10
And S4_B repository contains only: 19
And S4_C repository contains only: 31
And the values observed by the S4_A transactional action with ID 1 were exactly (and in the same order): [3, 4, 5, 6, 7, 8, 9, 10]
And the values observed by the S4_A transactional action with ID 2 were exactly (and in the same order): [3, 4, 5, 6, 7, 8, 9, 10]
And the values observed by the S4_A transactional action with ID 3 were exactly (and in the same order): [3, 4, 5, 6, 7, 8, 9, 10]
And the values observed by the S4_B transactional action were exactly (and in the same order): [12, 13, 14, 15, 16, 17, 18, 19]
And the values observed by the S4_C transactional action were exactly (and in the same order): [24, 25, 26, 27, 28, 29, 30]