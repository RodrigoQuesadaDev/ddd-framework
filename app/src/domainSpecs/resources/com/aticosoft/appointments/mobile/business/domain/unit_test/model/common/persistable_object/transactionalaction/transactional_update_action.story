Transactional action observation behavior.

Lifecycle:
Before:
Given no data
And transactional actions configuration is reset

Scenario: simple update

Given 1 transactional actions defined for S1 object
And 1 transactional action defined for S1 object that observes update changes with their previous value
When I insert S1 objects [3]
And after that I sequentially change S1 object 3 into the next values (one after another; same transaction) [7, 11, 13]
And after that I sequentially change S1 object 13 into the next values (one after another; same transaction) [17, 19, 23]
And after that I sequentially change S1 object 23 into the next values (one after another; same transaction) [29, 31, 37]
Then the values observed by the S1 transactional update action were exactly (and in the same order):
{transformer=FROM_LANDSCAPE}
| previousValue | 3  | 13 | 23 |
| value         | 13 | 23 | 37 |

Scenario: multiple updates to created value

Given S2 actions have priority values: [100, 90, 80]
And the S2 transactional action with priority 90 increments values of S2 objects smaller than 9
When I insert S2 objects [7]
Then the values observed by the S2 transactional update action were exactly (and in the same order; including their priority):
{transformer=FROM_LANDSCAPE}
| priority      | 100 | 90 | 100 | 90 | 100 | 90 | 80 |
| previousValue | 7   | 7  | 7   | 7  | 7   | 7  | 7  |
| value         | 7   | 7  | 8   | 8  | 9   | 9  | 9  |

Scenario: multiple updates to existing value

Given S3 repository contains: 11
And S3 actions have priority values: [100, 90, 80]
And the S3 transactional action with priority 90 increments values of S3 objects smaller than 9
When I change S3 object 11 into 17
Then the values observed by the S3 transactional update action were exactly (and in the same order; including their priority):
{transformer=FROM_LANDSCAPE}
| priority      | 100 | 90 | 100 | 90 | 100 | 90 | 80 |
| previousValue | 11  | 11 | 11  | 11 | 11  | 11 | 11 |
| value         | 17  | 17 | 18  | 18 | 19  | 19 | 19 |

!-- TODO What happens when nested entities/embeddeds change??? Hmmm... maybe just need a way to get to the parent/root entity from nested entities/embeddeds?
