Asynchronous observation of persistable object changes.

Scenario: main scenario

Given no data
And I subscribe to the EntityListener of a <entityType> entity
!-- TODO insert at once (single service call, like with transaction actions stories)
When I insert [1, 2, 3, 4, 5]
And after that I delete [2]
And after that I change 1 into 2
And after that I insert [6, 7, 8, 9, 10]
And after that I delete [6, 4, 8, 3, 5]
And after that I change 7 into 8
And after that I change 2 into 4
And after that I change 8 into 6
And after that I delete [4, 9, 6]
Then the entity change events observed were
{transformer=FROM_LANDSCAPE}
| type     | ADD  | ADD  | ADD  | ADD  | ADD  | REMOVE | UPDATE | ADD  | ADD  | ADD  | ADD  | ADD  | REMOVE | REMOVE | REMOVE | REMOVE | REMOVE | UPDATE | UPDATE | UPDATE | REMOVE | REMOVE | REMOVE |
| previous | null | null | null | null | null | 2      | 1      | null | null | null | null | null | 6      | 4      | 8      | 3      | 5      | 7      | 2      | 8      | 4      | 9      | 6      |
| current  | 1    | 2    | 3    | 4    | 5    | null   | 2      | 6    | 7    | 8    | 9    | 10   | null   | null   | null   | null   | null   | 8      | 4      | 6      | null   | null   | null   |

Examples:
| entityType |
| PARENT     |
| CHILD      |