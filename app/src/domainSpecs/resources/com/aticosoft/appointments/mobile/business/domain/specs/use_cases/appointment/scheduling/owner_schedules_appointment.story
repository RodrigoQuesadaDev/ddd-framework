Narrative:

In order to schedule an appointment for a given client
As a business owner
I want to be able to manually schedule appointments

!-- TODO use "after scenario steps" for validation-related stuff?, so that it can be externalized and reused? (so that sh*t is not repeated for creation AND modification)
!-- ... thinking about it maybe validation-related concerns should be separated into a different story and specify creation AND modification scenarios...

GivenStories: com/aticosoft/appointments/mobile/business/domain/specs/use_cases/client/given_stories/given_some_existing_clients.story

Scenario: set-up initial appointments

Given no appointments scheduled

Scenario: successful booking

Given a default configuration
When the owner schedules an appointment for <client> on <time>
Then an appointment is scheduled for <client> on <time>

Examples:
| client            | time                           |
| Harold Meyer      | 2015-07-17 from 15:00 to 16:00 |
| Teresa Turner     | 2015-08-05 from 01:45 to 02:15 |
| Melissa Hernandez | 2015-08-21 from 12:30 to 13:00 |

Scenario: successful overlapping booking

Given a default configuration
And a maximum number of concurrent appointments equal to <max_conc>
When the owner schedules an appointment for <client> on <time>
Then an appointment is scheduled for <client> on <time>

Examples:
| max_conc | client            | time                           |
| 1        | Harold Meyer      | 2015-09-17 from 15:00 to 16:00 |
| 2        | Teresa Turner     | 2015-09-17 from 15:00 to 16:00 |
| 3        | Melissa Hernandez | 2015-09-17 from 15:00 to 16:00 |
| 1        | Marie Austin      | 2015-10-05 from 15:00 to 16:00 |
| 2        | John Howell       | 2015-10-05 from 14:30 to 15:30 |
| 2        | Kathleen Wilson   | 2015-10-05 from 14:45 to 15:00 |
| 3        | Earl Wood         | 2015-10-05 from 14:45 to 15:30 |

Scenario: unsuccessful overlapping booking

Given a default configuration
And a maximum number of concurrent appointments equal to <max_conc>
When the owner schedules an appointment for <client> on <time>
Then the system throws an Exception indicating the schedule time is not allowed due to the configured maximum number of concurrent appointments

Examples:
| max_conc | client       | time                           |
| 1        | Earl Wood    | 2015-07-17 from 15:00 to 16:00 |
| 1        | Jesse Kim    | 2015-08-05 from 02:00 to 02:30 |
| 3        | Judith Cook  | 2015-10-05 from 15:00 to 16:00 |
| 3        | Jeremy Berry | 2015-10-05 from 14:45 to 15:15 |
