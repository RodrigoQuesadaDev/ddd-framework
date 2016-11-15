Run-once transactional actions run only once per object per transaction.

Lifecycle:
Before:
Given no data
And transactional actions configuration is reset

Scenario: action modifies the same object

Given 1 transactional action defined for S1 object, configured to run only once
And the S1 transactional action increments values
When I insert S1 objects [3, 7, 11]
Then S1 repository contains only: 4, 8, 12
And the values observed by the S1 transactional action were exactly (and in the same order): [3, 7, 11]

Scenario: action modifies different object

Given 1 transactional action defined for S2 object, configured to run only once
And the S2 transactional action increments existing [value - 7] values
When I insert S2 objects [17, 24, 31]
Then S2 repository contains only: 18, 25, 31
And the values observed by the S2 transactional action were exactly (and in the same order): [17, 24, 31]