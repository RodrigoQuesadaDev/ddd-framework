Event actions' execution order should be maintained between app runs.

Scenario: scenario 1

Given SAMPLE actions are defined like: [id1:p0, id2:p0, id3:p0, id4:p0, id5:p0, id6:p0, id7:p0, id8:p0, id9:p0, id10:p0]
And action events order is randomized by EventActionsManager
When event SAMPLE occurs with value 3
Then SAMPLE actions produce the next values (in any order):
[id1:3, id2:3, id3:3, id4:3, id5:3, id6:3, id7:3, id8:3, id9:3, id10:3]

NEW MANAG!!!
When event SAMPLE occurs with value 3
Then SAMPLE actions produces exactly the same previous values
