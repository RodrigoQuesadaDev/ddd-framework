Event action queries.

Scenario: single event at a time

!-- P = Processed, NP = Not Processed, K = Kept, NK = Not Kept, ALL = All, v = Value
Given event SINGLE_EVENT_QUERY has 1 action defined
And the query type being used for SINGLE_EVENT_QUERY events is <queryType>
When the next SINGLE_EVENT_QUERY event sequence occurs: [v3:NP&NK, v7:P&NK, v11:K, v13:NP&NK, v17:P&NK, v19:K]
Then SINGLE_EVENT_QUERY actions produce the next values in order: <result>
And only the next SINGLE_EVENT_QUERY events are left: [11, 19]

Examples:
| queryType | result                 |
| NP&NK     | [3, 13]                |
| P&NK      | [7, 17]                |
| K         | [11, 19]               |
| ALL       | [3, 7, 11, 13, 17, 19] |

Scenario: multiple events each time

!-- P = Processed, NP = Not Processed, K = Kept, NK = Not Kept, ALL = All, v = Value
Given event MULTIPLE_EVENTS_QUERY has 1 action defined
And the query type being used for MULTIPLE_EVENTS_QUERY events is <queryType>
And MULTIPLE_EVENTS_QUERY action outputs the average of the events it queries
When the next MULTIPLE_EVENTS_QUERY event sequence occurs: [v3:NP&NK, v7:P&NK, v11:K, v13:NP&NK, v17:P&NK, v19:K, v23:NP&NK, v29:P&NK, v31:K]
Then MULTIPLE_EVENTS_QUERY actions produce the next values in order: <result>
And only the next MULTIPLE_EVENTS_QUERY events are left: [11, 19, 31]

Examples:
| queryType | result  |
| NP&NK     | [13]    |
| P&NK      | [17.66] |
| K         | [20.33] |
| ALL       | [17]    |