Narrative:

In order to schedule an appointment for a given client
As a business owner
I want to be able to manually schedule appointments

GivenStories: com/aticosoft/appointments/mobile/business/domain/specs/use_cases/client/given_stories/given_some_existing_clients.story

Scenario: set-up initial appointments

Given no appointments scheduled

Scenario: successful booking

Given a default configuration
And a maximum number of concurrent appointments equal to <max_conc>
When the owner schedules an appointment for <client> on <time>
Then an appointment is scheduled for <client> on <time>

Examples:
| max_conc | client            | time                           |
| 1        | Harold Meyer      | 2015-09-17 from 15:00 to 16:00 |
| 1        | Teresa Turner     | 2015-10-05 from 01:45 to 02:15 |
| 1        | Melissa Hernandez | 2015-10-21 from 12:30 to 13:00 |
| 2        | Marie Austin      | 2015-09-17 from 15:00 to 16:00 |
| 2        | John Howell       | 2015-10-05 from 01:45 to 02:15 |
| 2        | Kathleen Wilson   | 2015-10-21 from 12:30 to 13:00 |
| 3        | Earl Wood         | 2015-09-17 from 15:00 to 16:00 |
| 3        | Jesse Kim         | 2015-10-05 from 01:45 to 02:15 |
| 3        | Judith Cook       | 2015-10-21 from 12:30 to 13:00 |