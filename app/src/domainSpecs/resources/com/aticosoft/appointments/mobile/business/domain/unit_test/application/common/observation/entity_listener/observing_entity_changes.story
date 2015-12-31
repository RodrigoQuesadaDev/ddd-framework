Narrative:

As a developer
I want to be able to be able to subscribe to an EntityListener
So that I can observe the changes occurring to entities of a given type

Scenario: scenario description

Given no data
And I subscribe to the EntityListener of a <entityType> entity
When I insert [1, 2, 3, 4, 5]
And after that I insert [6, 7, 8, 9, 10]
And after that I delete [2, 4, 6, 8]
And after that I change 1 into 2
And after that I change 7 into 8
And after that I change 2 into 4
And after that I change 8 into 6
Then the entity change events observed were
{transformer=FROM_LANDSCAPE}
| type     | ADD  | ADD  | ADD  | ADD  | ADD  | ADD  | ADD  | ADD  | ADD  | ADD  | REMOVE | REMOVE | REMOVE | REMOVE | UPDATE | UPDATE | UPDATE | UPDATE |
| previous | null | null | null | null | null | null | null | null | null | null | 2      | 4      | 6      | 8      | 1      | 7      | 2      | 8      |
| current  | 1    | 2    | 3    | 4    | 5    | 6    | 7    | 8    | 9    | 10   | null   | null   | null   | null   | 2      | 8      | 4      | 6      |

Examples:
| entityType |
| PARENT     |
| CHILD      |