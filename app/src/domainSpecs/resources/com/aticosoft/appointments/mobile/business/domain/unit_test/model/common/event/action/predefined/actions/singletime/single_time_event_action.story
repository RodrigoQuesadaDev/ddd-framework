A single-time event action processes an event a single time.

Scenario: default query

Given a SingleEventAction that doesn't override the querySpecification property
Then querySpecification property returns the predefined FIFO query