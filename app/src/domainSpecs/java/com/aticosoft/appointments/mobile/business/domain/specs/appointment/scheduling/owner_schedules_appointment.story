Meta:

Narrative:

In order to schedule an appointment for a given client
As a business owner
I want to be able to manually schedule appointments

Scenario: scenario 1

Given an empty calendar
When the owner schedules an appointment for <client> on <date>
Then an appointment is scheduled for <client> on <date>

Examples:
| client       | date                |
| Harold Meyer | 2015-09-17 at 15:00 |
