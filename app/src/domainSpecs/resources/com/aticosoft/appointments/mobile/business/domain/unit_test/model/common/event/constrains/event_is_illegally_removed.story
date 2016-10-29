Event removal should never be performed by services or actions.

Scenario: set-up

Given event SAMPLE has 1 actions defined

Scenario: event action tries to remove event

Given SAMPLE action removes event
When event SAMPLE occurs with value 3
Then the system throws an exception indicating an action illegally tried to remove the event

Scenario: service tries to remove event

Given the 1st SAMPLE action that gets executed marks it to be kept 1 time
When event SAMPLE occurs with value 3
And event with value 3 is removed by a service
Then the system throws an exception indicating a service illegally tried to remove the event