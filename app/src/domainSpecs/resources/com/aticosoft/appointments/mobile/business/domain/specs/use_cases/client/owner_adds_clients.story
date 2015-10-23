Narrative:

In order to keep contact information about my clients
As a business owner
I want to be able to manually add clients to my contact list

Scenario: scenario 1

Given no clients
When the owner adds a client with name <name> and birthDate <birthDate>
Then a client with name <name> and birthDate <birthDate> exists in the system

Examples:
| name          | birthDate  |
| Teresa Turner | 1990-08-09 |
| Marie Austin  | 1986-03-30 |
| Earl Wood     | 1983-09-14 |
