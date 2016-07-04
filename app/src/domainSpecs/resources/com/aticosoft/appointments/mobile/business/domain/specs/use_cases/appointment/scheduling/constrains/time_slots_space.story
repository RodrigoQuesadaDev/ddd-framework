Multi-booking feature.

Scenario: successful overlapping booking

Given <max_conc> concurrent appointment at max
When the owner schedules an appointment for <client> on <time>
Then an appointment is scheduled for <client> on <time>

Examples:
| max_conc | client            | time                           |
|-- shorter
| 1        | Harold Meyer      | 2015-06-05 from 01:45 to 02:15 |
|-- longer
| 1        | Teresa Turner     | 2015-07-17 from 15:00 to 16:00 |
|-- the rest
| 1        | Harold Meyer      | 2015-09-17 from 15:00 to 16:00 |
| 2        | Teresa Turner     | 2015-09-17 from 15:00 to 16:00 |
| 3        | Melissa Hernandez | 2015-09-17 from 15:00 to 16:00 |
| 1        | Marie Austin      | 2015-10-05 from 15:00 to 16:00 |
| 2        | John Howell       | 2015-10-05 from 14:30 to 15:30 |
| 2        | Kathleen Wilson   | 2015-10-05 from 14:45 to 15:00 |
| 3        | Earl Wood         | 2015-10-05 from 14:45 to 15:30 |
| 1        | Harold Meyer      | 2015-11-19 from 14:45 to 15:15 |
| 2        | Teresa Turner     | 2015-11-19 from 14:30 to 15:30 |
| 3        | Melissa Hernandez | 2015-11-19 from 14:00 to 16:00 |
| 4        | Marie Austin      | 2015-11-19 from 13:00 to 17:00 |

Scenario: unsuccessful overlapping booking

Given <max_conc> concurrent appointment at max
When the owner schedules an appointment for <client> on <time>
Then the system throws an Exception indicating there is not enough space available for scheduling the appointment at the given time

Examples:
| max_conc | client       | time                           |
|-- same
| 1        | Earl Wood    | 2015-06-05 from 01:45 to 02:15 |
|-- inside
| 1        | Earl Wood    | 2015-06-05 from 01:45 to 02:00 |
| 1        | Earl Wood    | 2015-06-05 from 02:00 to 02:15 |
|-- partially outside
| 1        | Earl Wood    | 2015-06-05 from 02:00 to 02:30 |
| 1        | Earl Wood    | 2015-06-05 from 01:30 to 02:00 |
|-- totally outside
| 1        | Earl Wood    | 2015-06-05 from 01:30 to 02:30 |
| 1        | Earl Wood    | 2015-06-05 from 01:00 to 03:00 |
| 1        | Earl Wood    | 2015-06-05 from 00:00 to 04:00 |
|-- same (longer)
| 1        | Jesse Kim    | 2015-07-17 from 15:00 to 16:00 |
|-- inside (longer)
| 1        | Jesse Kim    | 2015-07-17 from 15:15 to 15:45 |
| 1        | Jesse Kim    | 2015-07-17 from 15:00 to 15:45 |
| 1        | Jesse Kim    | 2015-07-17 from 15:15 to 16:00 |
|-- partially outside (longer)
| 1        | Jesse Kim    | 2015-07-17 from 15:15 to 16:15 |
| 1        | Jesse Kim    | 2015-07-17 from 15:30 to 16:30 |
| 1        | Jesse Kim    | 2015-07-17 from 14:45 to 15:45 |
| 1        | Jesse Kim    | 2015-07-17 from 14:30 to 15:30 |
|-- totally outside (longer)
| 1        | Jesse Kim    | 2015-07-17 from 14:45 to 16:15 |
| 1        | Jesse Kim    | 2015-07-17 from 14:00 to 17:00 |
| 1        | Jesse Kim    | 2015-07-17 from 13:00 to 18:00 |
|-- same (bigger max_conc)
| 3        | Jeremy Berry | 2015-10-05 from 14:45 to 15:30 |
|-- inside (bigger max_conc)
| 3        | Jeremy Berry | 2015-10-05 from 15:00 to 15:15 |
| 3        | Jeremy Berry | 2015-10-05 from 15:00 to 15:30 |
| 3        | Jeremy Berry | 2015-10-05 from 14:45 to 15:15 |
|-- partially outside (bigger max_conc)
| 3        | Jeremy Berry | 2015-10-05 from 14:30 to 15:15 |
| 3        | Jeremy Berry | 2015-10-05 from 14:15 to 15:15 |
| 3        | Jeremy Berry | 2015-10-05 from 15:00 to 15:45 |
| 3        | Jeremy Berry | 2015-10-05 from 15:00 to 16:00 |
| 3        | Jeremy Berry | 2015-10-05 from 15:00 to 16:15 |
|-- totally outside (bigger max_conc)
| 3        | Jeremy Berry | 2015-10-05 from 14:30 to 15:45 |
| 3        | Jeremy Berry | 2015-10-05 from 14:30 to 16:15 |
| 3        | Jeremy Berry | 2015-10-05 from 14:15 to 16:15 |
| 3        | Jeremy Berry | 2015-10-05 from 14:00 to 17:00 |
| 3        | Jeremy Berry | 2015-10-05 from 13:00 to 18:00 |