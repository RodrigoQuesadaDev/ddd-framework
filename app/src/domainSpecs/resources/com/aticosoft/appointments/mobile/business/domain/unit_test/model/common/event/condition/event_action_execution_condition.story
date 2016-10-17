An action should be able to specify code in the for of a condition, in order to decide whether an
event should be processed by it or not.

Scenario: conditions are specified

!-- P = Prime, E = Even, A = All
Given SAMPLE actions are defined like: [P:p100, E:p90, A:p80]
And the 3rd SAMPLE action that gets executed increments its value: 5 times
When event SAMPLE occurs with value 11
Then SAMPLE actions produce the next values in order:
[P:11, A:12, E:12, A:13, P:13, A:14, E:14, A:15, A:16, E:16, A:16]

Scenario: event is modified by code that evaluates condition

Given BAD_COND_MODIFIES action modifies the event during condition evaluation
And the next step might throw an exception
When event BAD_COND_MODIFIES occurs with value 17
Then the system throws an exception indicating an action illegally modified the event

Scenario: condition is added after event action has been initialized

Given the next step might throw an exception
When condition for BAD_COND_ADDED_AFTER_INIT action is added after it's been initialized
Then the system throws an exception indicating a condition was added to the action after its initialization