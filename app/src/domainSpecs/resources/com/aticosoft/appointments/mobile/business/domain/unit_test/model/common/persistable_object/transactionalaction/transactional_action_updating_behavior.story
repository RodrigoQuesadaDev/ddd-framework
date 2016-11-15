Transactional action behavior when updating data.

Lifecycle:
Before:
Given no data
And transactional actions configuration is reset

Scenario: set-up

!-- id = ID,
Given 1 transactional action defined for S1 object
And 2 transactional actions defined for S2 object like this: [id1:p100, id2:p90]

Scenario: transactional update to same object successes

Given S1 transactional action changes value 3 to 7
When I insert S1 objects [3]
Then S1 repository contains only: 7
And the values observed by the S1 transactional action were exactly (and in the same order): [3]

Scenario: transactional update to different object successes

Given S1 repository contains: 7
And S1 transactional action changes value 7 to 11
When I insert S1 objects [3]
Then S1 repository contains only: 3, 11
And the values observed by the S1 transactional action were exactly (and in the same order): [3]

Scenario: transactional update to same object fails

Given S1 transactional action changes value 3 to 7
And S1 transactional action fails when it completes
And the next step might throw an exception
When I insert S1 objects [3]
Then S1 repository contains no data
And the values observed by the S1 transactional action were exactly (and in the same order): [3]

Scenario: transactional update to different object fails

Given S1 repository contains: 7
And S1 transactional action changes value 7 to 11
And S1 transactional action fails when it completes
And the next step might throw an exception
When I insert S1 objects [3]
Then S1 repository contains only: 7
And the values observed by the S1 transactional action were exactly (and in the same order): [3]

Scenario: one action removes a value before another one process it

Given S2 repository contains: 3
And the S2 transactional action with ID 1 removes values
And the S2 transactional action with ID 2 increments values
When I increment S2 object with value 3
Then S2 repository contains no data
And the values observed by the S2 transactional action with ID 1 were exactly (and in the same order): [4]
And the values observed by the S2 transactional action with ID 2 were exactly (and in the same order): []