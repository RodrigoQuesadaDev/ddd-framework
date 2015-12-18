Narrative:

In order to schedule an appointment for a given client
As a business owner
I want to be able to manually schedule appointments

!--TODO change examples when end-time is added  (schedules should be ranges and not just instants of time)

GivenStories: com/aticosoft/appointments/mobile/business/domain/specs/use_cases/client/given_stories/given_some_existing_clients.story

Scenario: successful booking

Given no appointments scheduled
And a maximum number of concurrent appointments equal to <max_conc>
When the owner schedules an appointment for <client> on <date>
Then an appointment is scheduled for <client> on <date>

Examples:
| max_conc | client            | date                |
| 1        | Harold Meyer      | 2015-09-17 at 15:00 |
| 1        | Teresa Turner     | 2015-10-05 at 01:45 |
| 1        | Melissa Hernandez | 2015-10-21 at 12:30 |
| 2        | Marie Austin      | 2015-09-17 at 15:00 |
| 2        | John Howell       | 2015-10-05 at 01:45 |
| 2        | Kathleen Wilson   | 2015-10-21 at 12:30 |
| 3        | Earl Wood         | 2015-09-17 at 15:00 |
| 3        | Jesse Kim         | 2015-10-05 at 01:45 |
| 3        | Judith Cook       | 2015-10-21 at 12:30 |