Transactional actions order is based on their priority.

Lifecycle:
Before:
Given no data

Scenario: same priority

Given SAME_PRIORITY actions have priority values: [100, 100, 100, -70, -70, -70]
When I insert SAME_PRIORITY object with value 7
Then SAME_PRIORITY actions produce the next values in order:
[a100:7, a100:7, a100:7, a-70:7, a-70:7, a-70:7]

Scenario: different priority

Given DIFFERENT_PRIORITY actions have priority values: [900, 100, 10, -30, -500]
When I insert DIFFERENT_PRIORITY object with value 11
Then DIFFERENT_PRIORITY actions produce the next values in order:
[a900:11, a100:11, a10:11, a-30:11, a-500:11]

Scenario: default priority

Given DEFAULT_PRIORITY actions have priority values: [1, null, -1]
When I insert DEFAULT_PRIORITY object with value 13
Then DEFAULT_PRIORITY actions produce the next values in order:
[a1:13, a0:13, a-1:13]