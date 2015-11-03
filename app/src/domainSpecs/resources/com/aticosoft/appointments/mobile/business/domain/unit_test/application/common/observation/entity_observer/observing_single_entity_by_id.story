Narrative:

In order to observe a single entity by its id
As a developer
I want to be able to subscribe to an EntityObserver

Scenario: scenario 1

Given data [{p:1, c:1}, {p:2, c:2}, {p:3, c:3}, {p:4, c:4}, {p:5, c:5}]
And observation filter <filter>
And I'm observing parent entity with value 3
When later I add [{p:6, c:6}, {p:7, c:7}, {p:8, c:8}, {p:9, c:9}, {p:10, c:10}]
And later I remove parents [7, 9]
And later I change child of parent 3 to 33
And later I change parent 3 to 23
And later I change child of parent 23 to 34
And later I change parent 23 to 24
When later I add [{p:11, c:11}, {p:12, c:12}, {p:13, c:13}, {p:14, c:14}, {p:15, c:15}]
And later I remove parents [12, 14]
And later I change child of parent 24 to 35
And later I change parent 24 to 3
And later I remove parent [3]
Then later the values observed were <result>

Examples:
| filter                           | result                                                                              |
| DEFAULT                          | [{p:3, c:3}, {p:23, c:33}, {p:24, c:34}, {p:3, c:35}, null]                         |
| ODD_CHILD                        | [{p:3, c:3}, {p:3, c:3}, {p:3, c:3}, {p:3, c:33}, {p:24, c:34}, {p:24, c:35}, null] |
| ODD_CHILD_REMOVED                | [{p:3, c:3}, {p:3, c:3}, null]                                                      |
| ODD_AND_DIVISIBLE_BY_THREE_CHILD | [{p:3, c:3}, {p:3, c:3}, {p:3, c:3}, {p:3, c:33}, {p:24, c:34}]                     |