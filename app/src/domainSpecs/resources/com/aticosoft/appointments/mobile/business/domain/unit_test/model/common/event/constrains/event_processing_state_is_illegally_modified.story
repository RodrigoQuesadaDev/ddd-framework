Event processing state should never be directly modified by services or actions.

Scenario: set-up

Given event SAMPLE has 1 actions defined

Scenario: event action tries to change processing state of event

Given SAMPLE action removes event
When event SAMPLE occurs with value 3
Then the system throws an exception indicating an action illegally tried to change the processing state of event

Scenario: service tries to change processing state of event

Given the 1st SAMPLE action that gets executed marks it to be kept 1 time
When event SAMPLE occurs with value 3
And event with value 3 is removed by a service
Then the system throws an exception indicating a service illegally tried to change the processing state of event

!--TODO processing state change is forbidden!!!

* Keep
* Unkeep?
* Clear?
* Processed
* Unprocessed?

Use examples table???!!!!?!?!!