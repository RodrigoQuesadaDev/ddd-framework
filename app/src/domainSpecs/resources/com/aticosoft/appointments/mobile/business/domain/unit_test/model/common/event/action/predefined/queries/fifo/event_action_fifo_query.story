Event action FIFO query.

Scenario: processing flags

Given a FIFO query
Then the query has the processed flag set to false
And the query has the kept flag set to false

Scenario: behavior

Given event SAMPLE has 1 action defined
And SAMPLE actions execution is suspended
When event SAMPLE occurs with value 3
And event SAMPLE occurs with value 7
And event SAMPLE occurs with value 11
And event SAMPLE occurs with value 13
And event SAMPLE occurs with value 17
And THREE_ACTIONS actions execution is resumed
Then SAMPLE actions produce the next values in order: [3, 7, 11, 13, 17]