Narrative:

In order to schedule an appointment
As an user
I want to be able to only choose a schedule date that correspond with predetermined time slots

GivenStories: com/aticosoft/appointments/mobile/business/domain/specs/use_cases/client/given_stories/given_some_existing_clients.story

Scenario: set-up time slot of 15 minutes

Given no appointments scheduled
Given a configured time slot of 15 minutes

Scenario: successful booking with time slot of 15 minutes

When the owner schedules an appointment for <client> on <time>
Then the appointment is successfully scheduled

Examples:
| client            | time                           |
| Harold Meyer      | 2015-09-17 from 15:00 to 15:15 |
| Teresa Turner     | 2015-10-05 from 01:45 to 02:00 |
| Melissa Hernandez | 2015-10-21 from 12:30 to 13:00 |
| Marie Austin      | 2015-12-21 from 15:00 to 16:00 |

Scenario: unsuccessful booking with time slot of 15 minutes

When the owner schedules an appointment for <client> on <time>
Then the system throws a TimeSlotValidationException indicating the schedule time doesn't comply with the time slot configuration

Examples:
| client            | time                           |
| Harold Meyer      | 2015-09-17 from 15:01 to 15:15 |
| Harold Meyer      | 2015-09-17 from 15:00 to 15:14 |
| Teresa Turner     | 2015-10-05 from 01:44 to 02:00 |
| Teresa Turner     | 2015-10-05 from 01:45 to 02:01 |
| Melissa Hernandez | 2015-10-21 from 12:31 to 13:00 |
| Melissa Hernandez | 2015-10-21 from 12:30 to 12:59 |
| Marie Austin      | 2015-12-21 from 15:03 to 15:18 |
| Marie Austin      | 2015-12-21 from 15:03 to 15:33 |
| Marie Austin      | 2015-12-21 from 15:03 to 16:03 |

Scenario: set-up time slot of 30 minutes

Given no appointments scheduled
Given a configured time slot of 30 minutes

Scenario: successful booking with time slot of 30 minutes

When the owner schedules an appointment for <client> on <time>
Then the appointment is successfully scheduled

Examples:
| client            | time                           |
| Harold Meyer      | 2015-09-17 from 15:00 to 15:30 |
| Teresa Turner     | 2015-10-05 from 01:30 to 02:00 |
| Melissa Hernandez | 2015-10-21 from 12:30 to 13:30 |
| Marie Austin      | 2015-12-21 from 15:00 to 17:00 |

Scenario: unsuccessful booking with time slot of 30 minutes

When the owner schedules an appointment for <client> on <time>
Then the system throws a TimeSlotValidationException indicating the schedule time doesn't comply with the time slot configuration

Examples:
| client            | time                           |
| Harold Meyer      | 2015-09-17 from 15:00 to 15:15 |
| Teresa Turner     | 2015-10-05 from 01:45 to 02:00 |
| Harold Meyer      | 2015-09-17 from 15:01 to 15:30 |
| Harold Meyer      | 2015-09-17 from 15:00 to 15:29 |
| Teresa Turner     | 2015-10-05 from 01:44 to 02:15 |
| Teresa Turner     | 2015-10-05 from 01:45 to 02:16 |
| Melissa Hernandez | 2015-10-21 from 12:31 to 13:30 |
| Melissa Hernandez | 2015-10-21 from 12:30 to 13:29 |
| Marie Austin      | 2015-12-21 from 15:03 to 15:33 |
| Marie Austin      | 2015-12-21 from 15:15 to 15:30 |
| Marie Austin      | 2015-12-21 from 15:15 to 16:45 |
| Marie Austin      | 2015-12-21 from 15:15 to 16:15 |