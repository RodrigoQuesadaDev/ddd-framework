Transactional action behavior when updating data.

Lifecycle:
Before:
Given no data
And actions don't modify any data

Scenario: set-up

!-- id = ID,
Given 1 transactional action defined for SAMPLE_1 object
And 2 transactional actions defined for SAMPLE_2 object like this: [id1:p100, id1:p90]
And 2 transactional actions defined for SAMPLE_3 object like this: [id1:p100, id1:p90]

Scenario: transactional update to same object successes

Given SAMPLE_1 transactional action changes value 3 to 7
When I insert SAMPLE_1 objects [3]
Then SAMPLE_1 repository contains only: 7

Scenario: transactional update to different object successes

Given SAMPLE_1 repository contains: 7
And SAMPLE_1 transactional action changes value 7 to 11
When I insert SAMPLE_1 objects [3]
Then SAMPLE_1 repository contains only: 3, 11

Scenario: transactional update to same object fails

Given SAMPLE_1 transactional action changes value 3 to 7
And SAMPLE_1 transactional action fails when it completes
And the next step might throw an exception
When I insert SAMPLE_1 objects [3]
Then SAMPLE_1 repository contains no data

Scenario: transactional update to different object fails

Given SAMPLE_1 repository contains: 7
And SAMPLE_1 transactional action changes value 7 to 11
And SAMPLE_1 transactional action fails when it completes
And the next step might throw an exception
When I insert SAMPLE_1 objects [3]
Then SAMPLE_1 repository contains only: 7

Scenario: actions are executed only once per transaction (no cycling)

Given the SAMPLE_2 transactional action with ID 1 increments odd values
And the SAMPLE_2 transactional action with ID 2 increments even values
When I insert SAMPLE_2 objects [3]
Then SAMPLE_2 repository contains only: 5

Scenario: one action removes a value before another one process it

Given SAMPLE_3 repository contains: 3
And the SAMPLE_3 transactional action with ID 1 removes values
And the SAMPLE_3 transactional action with ID 2 increments values
When I increment SAMPLE_3 object with value 3
Then SAMPLE_3 repository contains no data