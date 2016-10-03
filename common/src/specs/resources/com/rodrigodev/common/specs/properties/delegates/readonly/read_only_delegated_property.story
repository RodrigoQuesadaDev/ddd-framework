ReadOnlyDelegate should not allow properties to be writable.

Scenario: property is read

Given the property is initialized with the value 13
Then the value of the property is 13

Scenario: property is written

Given the next step might throw an exception
When the property is set to 17
Then an UnsupportedOperationException is thrown
