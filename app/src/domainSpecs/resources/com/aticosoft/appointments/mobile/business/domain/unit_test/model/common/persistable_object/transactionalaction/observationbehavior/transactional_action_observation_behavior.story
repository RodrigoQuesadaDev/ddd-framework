Transactional action observation behavior.

Lifecycle:
Before:
Given no data
And transactional actions configuration is reset

Scenario: parent/child actions subscribed to a single object change type

Given 3 transactional actions defined for <objectType> object
And 1 transactional action defined for <objectType> object changes of type [ADD]
And 1 transactional action defined for <objectType> object changes of type [UPDATE]
And 1 transactional action defined for <objectType> object changes of type [REMOVE]
When I insert <objectType> objects [1, 2, 3, 4, 5]
And after that I delete <objectType> objects [2]
And after that I change <objectType> object 1 into 2
And after that I insert <objectType> objects [6, 7, 8, 9, 10]
And after that I delete <objectType> objects [6, 4, 8, 3, 5]
And after that I change <objectType> object 7 into 8
And after that I change <objectType> object 2 into 4
And after that I change <objectType> object 8 into 6
And after that I delete <objectType> objects [4, 9, 6]
Then the values produced by the <objectType> transactional actions were exactly (and in the same order):
{transformer=FROM_LANDSCAPE}
| type  | ADD | ADD | ADD | ADD | ADD | REMOVE | UPDATE | ADD | ADD | ADD | ADD | ADD | REMOVE | REMOVE | REMOVE | REMOVE | REMOVE | UPDATE | UPDATE | UPDATE | REMOVE | REMOVE | REMOVE |
| value | 1   | 2   | 3   | 4   | 5   | 2      | 2      | 6   | 7   | 8   | 9   | 10  | 6      | 4      | 8      | 3      | 5      | 8      | 4      | 6      | 4      | 9      | 6      |

Examples:
| objectType    |
| PARENT_SINGLE |
| CHILD_SINGLE  |

Scenario: action subscribed to multiple object change types

Given 4 transactional actions defined for SAMPLE_MULTI object
And 1 transactional action defined for SAMPLE_MULTI object changes of type [ADD, REMOVE]
And 1 transactional action defined for SAMPLE_MULTI object changes of type [ADD, UPDATE]
And 1 transactional action defined for SAMPLE_MULTI object changes of type [UPDATE, REMOVE]
And 1 transactional action defined for SAMPLE_MULTI object changes of type [ADD, UPDATE, REMOVE]
When I insert SAMPLE_MULTI objects [1, 2, 3, 4, 5]
And after that I delete SAMPLE_MULTI objects [2]
And after that I change SAMPLE_MULTI object 1 into 2
And after that I insert SAMPLE_MULTI objects [6, 7, 8, 9, 10]
And after that I delete SAMPLE_MULTI objects [6, 4, 8, 3, 5]
And after that I change SAMPLE_MULTI object 7 into 8
And after that I change SAMPLE_MULTI object 2 into 4
And after that I change SAMPLE_MULTI object 8 into 6
And after that I delete SAMPLE_MULTI objects [4, 9, 6]
Then the values produced by the SAMPLE_MULTI transactional action of type [ADD, REMOVE] were exactly (and in the same order):
[1, 2, 3, 4, 5, 2, 6, 7, 8, 9, 10, 6, 4, 8, 3, 5, 4, 9, 6]
Then the values produced by the SAMPLE_MULTI transactional action of type [ADD, UPDATE] were exactly (and in the same order):
[1, 2, 3, 4, 5, 2, 6, 7, 8, 9, 10, 8, 4, 6]
Then the values produced by the SAMPLE_MULTI transactional action of type [UPDATE, REMOVE] were exactly (and in the same order):
[2, 2, 6, 4, 8, 3, 5, 8, 4, 6, 4, 9, 6]
Then the values produced by the SAMPLE_MULTI transactional action of type [ADD, UPDATE, REMOVE] were exactly (and in the same order):
[1, 2, 3, 4, 5, 2, 2, 6, 7, 8, 9, 10, 6, 4, 8, 3, 5, 8, 4, 6, 4, 9, 6]

Scenario: many updates to same object within a single transaction

Given 1 transactional action defined for SAMPLE_MANY_UPDATES object
And 1 transactional action defined for SAMPLE_MANY_UPDATES object changes of type [UPDATE]
When I insert SAMPLE_MANY_UPDATES objects [3]
And after that I sequentially change SAMPLE_MANY_UPDATES object 3 into the next values (one after another; same transaction) [7, 11, 13]
And after that I sequentially change SAMPLE_MANY_UPDATES object 13 into the next values (one after another; same transaction) [17, 19, 23]
And after that I sequentially change SAMPLE_MANY_UPDATES object 23 into the next values (one after another; same transaction) [29, 31, 37]
Then the values produced by the SAMPLE_MANY_UPDATES transactional action of type [UPDATE] were exactly (and in the same order): [13, 23, 37]

Scenario: updates to different objects within a single transaction

Given 1 transactional action defined for SAMPLE_UPDATE_DIFFERENT_OBJECTS object
And 1 transactional action defined for SAMPLE_UPDATE_DIFFERENT_OBJECTS object changes of type [UPDATE]
When I insert SAMPLE_UPDATE_DIFFERENT_OBJECTS objects [7, 29]
And after that I change SAMPLE_UPDATE_DIFFERENT_OBJECTS objects (same transaction): 7 into 11 and 29 into 31
And after that I change SAMPLE_UPDATE_DIFFERENT_OBJECTS objects (same transaction): 11 into 13 and 31 into 37
And after that I change SAMPLE_UPDATE_DIFFERENT_OBJECTS objects (same transaction): 13 into 17 and 37 into 39
Then the values produced by the SAMPLE_UPDATE_DIFFERENT_OBJECTS transactional action of type [UPDATE] were exactly (and in the same order): [11, 31, 13, 37, 17, 39]

Scenario: many actions for the same object change type

Given 3 transactional actions defined for SAMPLE_MANY_ACTIONS_SAME_TYPE object
And 3 transactional actions defined for SAMPLE_MANY_ACTIONS_SAME_TYPE object changes of type [UPDATE]
When I insert SAMPLE_MANY_ACTIONS_SAME_TYPE objects [7, 29, 43]
And after that I increment all existing SAMPLE_MANY_ACTIONS_SAME_TYPE objects (same transaction)
Then the values produced by every SAMPLE_MANY_ACTIONS_SAME_TYPE transactional action were exactly (and in the same order): [8, 30, 44]

Scenario: object is created|updated|removed by single service call; single action

Given 1 transactional actions defined for SAMPLE_SINGLE_CALL_1 object
And 1 transactional action defined for SAMPLE_SINGLE_CALL_1 object changes of type [ADD, UPDATE, REMOVE]
And SAMPLE_SINGLE_CALL_1 repository contains: 7
When SAMPLE_SINGLE_CALL_1 object \$value is <serviceCallActions> by a single service call
Then the values produced by the SAMPLE_SINGLE_CALL_1 transactional action of type [ADD, UPDATE, REMOVE] were exactly (and in the same order; using list format): <producedValues>

Examples:
| value | serviceCallActions                    | producedValues |
| 3     | created, incremented and then removed | [4]            |
| 3     | created and incremented               | [4]            |
| 7     | incremented and then removed          | [8]            |
| 3     | created and then removed              | [3]            |

Scenario: object is created|updated|removed by single service call; multiple actions

Given 3 transactional actions defined for SAMPLE_SINGLE_CALL_2 object
And 1 transactional action defined for SAMPLE_SINGLE_CALL_2 object changes of type [ADD]
And 1 transactional action defined for SAMPLE_SINGLE_CALL_2 object changes of type [UPDATE]
And 1 transactional action defined for SAMPLE_SINGLE_CALL_2 object changes of type [REMOVE]
And SAMPLE_SINGLE_CALL_2 repository contains: 7
When SAMPLE_SINGLE_CALL_2 object \$value is <serviceCallActions> by a single service call
Then the values produced by the SAMPLE_SINGLE_CALL_2 transactional actions were exactly (in any order; using list format): <producedValues>

Examples:
| value | serviceCallActions                    | producedValues              |
| 3     | created, incremented and then removed | [ADD:4, UPDATE:4, REMOVE:4] |
| 3     | created and incremented               | [ADD:4, UPDATE:4]           |
| 7     | incremented and then removed          | [UPDATE:8, REMOVE:8]        |
| 3     | created and then removed              | [ADD:3, REMOVE:3]           |

Scenario: default object change types

Given 1 transactional actions defined for SAMPLE_DEFAULT object
And 1 transactional action defined for SAMPLE_DEFAULT object that doesn't specify any object change type
Then the SAMPLE_DEFAULT transactional action observes object changes of type [ADD, UPDATE]