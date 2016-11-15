Transactional actions order is based on their priority.

Lifecycle:
Before:
Given no data
And transactional actions configuration is reset

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

Scenario: TransactionalAction and TransactionalUpdateAction objects with different priorities

Given DIFFERENT_PRIORITY_MIXED actions have priority values: [100, 90, 80, 70, 60]
And DIFFERENT_PRIORITY_MIXED actions with priority 100 is of type TransactionalAction
And DIFFERENT_PRIORITY_MIXED actions with priority 90 is of type TransactionalUpdateAction
And DIFFERENT_PRIORITY_MIXED actions with priority 80 is of type TransactionalAction
And DIFFERENT_PRIORITY_MIXED actions with priority 70 is of type TransactionalUpdateAction
And DIFFERENT_PRIORITY_MIXED actions with priority 60 is of type TransactionalAction
When I insert DIFFERENT_PRIORITY_MIXED object with value 17
Then DIFFERENT_PRIORITY_MIXED actions produce the next values in order: [a100:17, a90:17, a80:17, a70:17, a60:17]

Scenario: recursive 1 (same value modified by first action)

Given RECURSIVE_1 actions have priority values: [100, 90, 80]
And the RECURSIVE_1 transactional action with priority 100 increments values once
When I insert RECURSIVE_1 object with value 19
Then RECURSIVE_1 actions produce the next values in order: [a100:19, a100:20, a90:20, a80:20]

Scenario: recursive 2 (same value modified by second action)

Given RECURSIVE_2 actions have priority values: [100, 90, 80]
And the RECURSIVE_2 transactional action with priority 90 increments values once
When I insert RECURSIVE_2 object with value 23
Then RECURSIVE_2 actions produce the next values in order: [a100:23, a90:23, a100:24, a90:24, a80:24]

Scenario: recursive 3 (different value modified by second action)

Given RECURSIVE_3 repository contains: 37
And RECURSIVE_3 actions have priority values: [100, 90, 80]
And the RECURSIVE_3 transactional action with priority 90 increments 37 value once
When I insert RECURSIVE_3 object with value 29
Then RECURSIVE_3 actions produce the next subsequence of values in the same order order: [a100:29, a90:29, a80:29]
Then RECURSIVE_3 actions produce the next subsequence of values in the same order order: [a100:38, a90:38, a80:38]
Then RECURSIVE_3 actions produce the next values in any order: [a100:29, a90:29, a80:29, a100:38, a90:38, a80:38]