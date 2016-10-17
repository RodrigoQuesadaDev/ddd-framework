An action can decide to receive an event either a single time or multiple times.

Scenario: single-time and multiple-times actions

!-- S = Single-time M = Multiple-times p = priority
Given SAMPLE actions are defined like: [M:p100, S:p90, M:p80, S:p70, M:p60]
And the 3rd SAMPLE action that gets executed increments its value (at least): 2 times
And the 4th SAMPLE action that gets executed increments its value (at least): 10 times
And the 5th SAMPLE action that gets executed increments its value (at least): 3 times
When event SAMPLE occurs with value 7
Then SAMPLE actions produce the next values in order:
[M:7, S:7, M:8, M:8, M:9, M:9, M:9, S:10, M:10, M:10, M:11, M:11, M:11, M:12, M:12, M:12, M:13, M:13, M:13, M:13]