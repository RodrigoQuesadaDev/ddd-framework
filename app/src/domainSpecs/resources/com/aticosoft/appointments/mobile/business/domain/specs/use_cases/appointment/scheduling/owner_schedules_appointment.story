Narrative:

In order to schedule an appointment for a given client
As a business owner
I want to be able to manually schedule appointments

GivenStories: com/aticosoft/appointments/mobile/business/domain/specs/use_cases/client/given_stories/given_some_existing_clients.story

Scenario: scenario 1

Given no appointments scheduled
When the owner schedules an appointment for <client> on <date>
Then an appointment is scheduled for <client> on <date>

Examples:
| client       | date                |
| Harold Meyer | 2015-09-17 at 15:00 |
| John Howell  | 2015-10-05 at 01:45 |
| Jeremy Berry | 2015-10-21 at 12:30 |
