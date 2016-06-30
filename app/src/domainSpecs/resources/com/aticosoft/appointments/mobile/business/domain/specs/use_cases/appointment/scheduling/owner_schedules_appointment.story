The owner schedules an appointment for a client.

Scenario: successful booking

When the owner schedules an appointment for <client> on <time>
Then an appointment is scheduled for <client> on <time>

Examples:
| client            | time                           |
| Harold Meyer      | 2015-07-17 from 15:00 to 16:00 |
| Teresa Turner     | 2015-08-05 from 01:45 to 02:15 |
| Melissa Hernandez | 2015-08-21 from 12:30 to 13:00 |