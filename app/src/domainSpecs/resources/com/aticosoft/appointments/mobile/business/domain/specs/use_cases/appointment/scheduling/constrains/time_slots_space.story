Multi-booking feature.

Scenario: successful overlapping booking

Given <max_conc> concurrent appointment at max
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

Given <max_conc> concurrent appointment at max
When the owner schedules an appointment for <client> on <time>
Then the system throws an Exception indicating there is not enough space available for scheduling the appointment at the given time

Examples:
| max_conc | client       | time                           |
| 1        | Earl Wood    | 2015-07-17 from 15:00 to 16:00 |
| 1        | Jesse Kim    | 2015-08-05 from 02:00 to 02:30 |
| 3        | Judith Cook  | 2015-10-05 from 15:00 to 16:00 |
| 3        | Jeremy Berry | 2015-10-05 from 14:45 to 15:15 |
