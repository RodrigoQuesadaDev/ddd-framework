PostInitializedPropertyDelegate implementation.

Scenario: value is read before being initialized

Given a PropertyInitializer object is created
And a property delegated by an PostInitializedPropertyDelegate is defined with a value of 3
And the next step might throw an exception
When the value of the defined property is read
Then an IllegalStateException indicating the property hasn't been initialized yet is thrown

Scenario: value is initialized twice

Given the defined property is initialized by the PropertyInitializer
And the next step might throw an exception
When the defined property is initialized by the PropertyInitializer
Then an IllegalStateException indicating the property has already been initialized is thrown

Scenario: value is read after being initialized

Given a PropertyInitializer object is created
And some properties delegated by an PostInitializedPropertyDelegate are defined with the values: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
When the defined properties are initialized by the PropertyInitializer
Then the read value of the defined properties are: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
