Illegal processing state for events.

Scenario: set-up

Given event SAMPLE has 1 actions defined

Scenario: event is marked as keep but left unprocessed

When an event SAMPLE is marked to be keep but left with an unprocessed state
Then the system throws an IllegalStateException indicating the event was set to an illegal processing state