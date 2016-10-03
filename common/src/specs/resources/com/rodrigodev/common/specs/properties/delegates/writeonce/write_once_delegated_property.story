WriteOnceDelegate should allow properties to be written only once.

Scenario: property is initialized

Given the property is initialized with the value 13
Then the value of the property is 13

When the property is set to 17
Then an IllegalStateException is thrown

Scenario: property is not initialized

Given the property is not initialized
When the property is read
Then an IllegalStateException is thrown

When the property is set to 23
Then the value of the property is 23

When the property is set to 29
Then an IllegalStateException is thrown
