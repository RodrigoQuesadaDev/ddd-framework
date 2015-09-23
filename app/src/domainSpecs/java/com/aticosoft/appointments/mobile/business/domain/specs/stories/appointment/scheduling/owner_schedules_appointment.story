Narrative:

In order to schedule an appointment for a given client
As a business owner
I want to be able to manually schedule appointments

GivenStories: com/aticosoft/appointments/mobile/business/domain/specs/stories/client/some_existing_clients.story

Scenario: scenario 1
Given no appointments scheduled
When the owner schedules an appointment for <client> on <date>
Then an appointment is scheduled for <client> on <date>

Examples:
| client      | date                |
| John Howell | 2015-09-17 at 15:00 |
