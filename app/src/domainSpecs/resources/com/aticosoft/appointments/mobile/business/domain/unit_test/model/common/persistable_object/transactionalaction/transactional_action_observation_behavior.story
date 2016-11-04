Transactional action observation behavior.

Lifecycle:
Before:
Given no data

Scenario: parent/child actions subscribed to a single object change type

Given 1 transactional action defined for <objectType> object changes of type [ADD]
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

Given 1 transactional action defined for <objectType> object changes of type [<changeTypes>]
When I insert <objectType> objects [1, 2, 3, 4, 5]
And after that I delete <objectType> objects [2]
And after that I change <objectType> object 1 into 2
And after that I insert <objectType> objects [6, 7, 8, 9, 10]
And after that I delete <objectType> objects [6, 4, 8, 3, 5]
And after that I change <objectType> object 7 into 8
And after that I change <objectType> object 2 into 4
And after that I change <objectType> object 8 into 6
And after that I delete <objectType> objects [4, 9, 6]
Then the values produced by the <objectType> transactional action of type were exactly (and in the same order): [<producedValues>]

Examples:
| objectType     | changeTypes         | producedValues                                                       |
| SAMPLE_MULTI_1 | ADD, REMOVE         | 1, 2, 3, 4, 5, 2, 6, 7, 8, 9, 10, 6, 4, 8, 3, 5, 4, 9, 6             |
| SAMPLE_MULTI_2 | ADD, UPDATE         | 1, 2, 3, 4, 5, 2, 6, 7, 8, 9, 10, 8, 4, 6                            |
| SAMPLE_MULTI_3 | UPDATE, REMOVE      | 2, 2, 6, 4, 8, 3, 5, 8, 4, 6, 4, 9, 6                                |
| SAMPLE_MULTI_4 | ADD, UPDATE, REMOVE | 1, 2, 3, 4, 5, 2, 2, 6, 7, 8, 9, 10, 6, 4, 8, 3, 5, 8, 4, 6, 4, 9, 6 |

Scenario: many updates to same object within a single transaction

And 1 transactional action defined for SAMPLE_MANY_UPDATES object changes of type [UPDATE]
When I insert SAMPLE_MANY_UPDATES objects [3]
And after that I sequentially change SAMPLE_MANY_UPDATES object 3 into the next values (one after another) [7, 11, 13]
And after that I sequentially change SAMPLE_MANY_UPDATES object 13 into the next values (one after another) [17, 19, 23]
And after that I sequentially change SAMPLE_MANY_UPDATES object 23 into the next values (one after another) [29, 31, 37]
Then the values produced by the SAMPLE_MANY_UPDATES transactional action of type [UPDATE] were exactly (and in the same order): [13, 23, 37]

Scenario: observe updates with previous value

And 1 transactional action defined for SAMPLE_UPDATE_WITH_PREVIOUS_VALUE object that observes update changes with their previous value
When I insert SAMPLE_UPDATE_WITH_PREVIOUS_VALUE objects [3]
And after that I sequentially change SAMPLE_UPDATE_WITH_PREVIOUS_VALUE object 3 into the next values (one after another) [7, 11, 13]
And after that I sequentially change SAMPLE_UPDATE_WITH_PREVIOUS_VALUE object 13 into the next values (one after another) [17, 19, 23]
And after that I sequentially change SAMPLE_UPDATE_WITH_PREVIOUS_VALUE object 23 into the next values (one after another) [29, 31, 37]
Then the values produced by the SAMPLE_UPDATE_WITH_PREVIOUS_VALUE transactional action of type [UPDATE] were exactly (and in the same order):
{transformer=FROM_LANDSCAPE}
| previous | 3  | 13 | 23 |
| current  | 13 | 23 | 37 |

Scenario: default object change types

Given 1 transactional action defined for SAMPLE_DEFAULT object that doesn't specify any object change type
When I insert SAMPLE_DEFAULT objects [3]
And after that I change SAMPLE_DEFAULT object 3 into 7
And after that I delete SAMPLE_DEFAULT objects [7]
Then the values produced by the SAMPLE_DEFAULT transactional actions were exactly (and in the same order):
{transformer=FROM_LANDSCAPE}
| type  | ADD | UPDATE |
| value | 3   | 7      |