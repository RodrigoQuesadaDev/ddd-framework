Events processing status is cleared out when the app starts.

Scenario: app starts-up

!-- Given app starts-up
Given an EventsProcessingStatusManager instance's resetProcessingStatus method has not been executed when is first created
Then the EventsProcessingStatusManager's resetProcessingStatus method has been executed

Scenario: resetProcessingStatus is executed

Given there are 10 different events with different processing status for each of their 5 actions
And the EventsProcessingStatusManager's resetProcessingStatus method has not been executed
When the EventsProcessingStatusManager's resetProcessingStatus method is executed
Then the processing status of each of the 10 different events is set to unprocessed and non-kept
And the EventsProcessingStatusManager's resetProcessingStatus method has been executed