Value objects must be read-only once they are created.

Scenario: value object is modified after creation

Given the next step might throw an exception
When a value object is created with value 3 and then immediately modified
Then the system throws a ValueObjectModificationException

Scenario: value object is modified after loading

Given a value object is created with value 7
And the next step might throw an exception
When the value object with value 7 is retrieved from the database and then modified
Then the system throws a ValueObjectModificationException