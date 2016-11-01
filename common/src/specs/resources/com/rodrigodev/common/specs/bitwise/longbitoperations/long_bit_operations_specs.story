LongBitOperations tests.

Scenario: sanity checks

!-- Java's long data type is a 64-bit two's complement integer.
Given constant EMPTY is equal to value 0
And constant ALL_BITS_SET is equal to inverting value 0
And constant ALL_BITS_SET is equal to value -1
And constant HIGHER_BIT_SET is equal to value -9223372036854775808
And constant HIGHER_BIT_SET is equal to 1 shifted to the left 63 times

Scenario: clear

Given data is <initialValue>
When data is set to the result of calling clear method on it
Then data is equal to value 0

Examples:
| initialValue   |
| EMPTY          |
| ALL_BITS_SET   |
| HIGHER_BIT_SET |
| PATTERN1       |
| PATTERN1_INV   |
| PATTERN2       |
| PATTERN2_INV   |

Scenario: setAllBits

Given data is <initialValue>
When data is set to the result of calling setAllBits method on it
Then data is equal to constant ALL_BITS_SET

Examples:
| initialValue   |
| EMPTY          |
| ALL_BITS_SET   |
| HIGHER_BIT_SET |
| PATTERN1       |
| PATTERN1_INV   |
| PATTERN2       |
| PATTERN2_INV   |

Scenario: isEmpty

Given data is <initialValue>
When boolean method isEmpty is called on data
Then the boolean result is <result>

Examples:
| initialValue   | result |
| EMPTY          | true   |
| ALL_BITS_SET   | false  |
| HIGHER_BIT_SET | false  |
| PATTERN1       | false  |
| PATTERN1_INV   | false  |
| PATTERN2       | false  |
| PATTERN2_INV   | false  |

Scenario: isFull

Given data is <initialValue>
When boolean method isFull is called on data
Then the boolean result is <result>

Examples:
| initialValue   | result |
| EMPTY          | false  |
| ALL_BITS_SET   | true   |
| HIGHER_BIT_SET | false  |
| PATTERN1       | false  |
| PATTERN1_INV   | false  |
| PATTERN2       | false  |
| PATTERN2_INV   | false  |

Scenario: setBit

Given data is <initialValue>
When data is set to the result of calling setBit method with parameter value <parameterValue> on it
Then data is equal to value <result>

Examples:
| initialValue   | parameterValue | result                                                                     |
| EMPTY          | 0              | 0b_00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000001 |
| ALL_BITS_SET   | 0              | 0b_11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111 |
| HIGHER_BIT_SET | 0              | 0b_10000000_00000000_00000000_00000000_00000000_00000000_00000000_00000001 |
| PATTERN1       | 0              | 0b_01010101_01010101_01010101_01010101_01010101_01010101_01010101_01010101 |
| PATTERN1_INV   | 0              | 0b_10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101011 |
| PATTERN2       | 0              | 0b_11111111_11111111_11111111_11111111_00000000_00000000_00000000_00000001 |
| PATTERN2_INV   | 0              | 0b_00000000_00000000_00000000_00000000_11111111_11111111_11111111_11111111 |
| EMPTY          | 63             | 0b_10000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000 |
| ALL_BITS_SET   | 63             | 0b_11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111 |
| HIGHER_BIT_SET | 63             | 0b_10000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000 |
| PATTERN1       | 63             | 0b_11010101_01010101_01010101_01010101_01010101_01010101_01010101_01010101 |
| PATTERN1_INV   | 63             | 0b_10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010 |
| PATTERN2       | 63             | 0b_11111111_11111111_11111111_11111111_00000000_00000000_00000000_00000000 |
| PATTERN2_INV   | 63             | 0b_10000000_00000000_00000000_00000000_11111111_11111111_11111111_11111111 |
| EMPTY          | 47             | 0b_00000000_00000000_10000000_00000000_00000000_00000000_00000000_00000000 |
| ALL_BITS_SET   | 47             | 0b_11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111 |
| HIGHER_BIT_SET | 47             | 0b_10000000_00000000_10000000_00000000_00000000_00000000_00000000_00000000 |
| PATTERN1       | 47             | 0b_01010101_01010101_11010101_01010101_01010101_01010101_01010101_01010101 |
| PATTERN1_INV   | 47             | 0b_10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010 |
| PATTERN2       | 47             | 0b_11111111_11111111_11111111_11111111_00000000_00000000_00000000_00000000 |
| PATTERN2_INV   | 47             | 0b_00000000_00000000_10000000_00000000_11111111_11111111_11111111_11111111 |
| EMPTY          | 19             | 0b_00000000_00000000_00000000_00000000_00000000_00001000_00000000_00000000 |
| ALL_BITS_SET   | 19             | 0b_11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111 |
| HIGHER_BIT_SET | 19             | 0b_10000000_00000000_00000000_00000000_00000000_00001000_00000000_00000000 |
| PATTERN1       | 19             | 0b_01010101_01010101_01010101_01010101_01010101_01011101_01010101_01010101 |
| PATTERN1_INV   | 19             | 0b_10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010 |
| PATTERN2       | 19             | 0b_11111111_11111111_11111111_11111111_00000000_00001000_00000000_00000000 |
| PATTERN2_INV   | 19             | 0b_00000000_00000000_00000000_00000000_11111111_11111111_11111111_11111111 |

Scenario: setBit bad parameters

Given data is <initialValue>
And the next step might throw an exception
When data is set to the result of calling setBit method with parameter value <parameterValue> on it
Then an Exception is thrown

Examples:
| initialValue | parameterValue |
| PATTERN1     | -1             |
| PATTERN2     | -1             |
| PATTERN1     | 64             |
| PATTERN2     | 64             |
| PATTERN1     | 987654         |
| PATTERN2     | 987654         |
| PATTERN1     | -987654        |
| PATTERN2     | -987654        |

Scenario: clearBit

Given data is <initialValue>
When data is set to the result of calling clearBit method with parameter value <parameterValue> on it
Then data is equal to value <result>

Examples:
| initialValue   | parameterValue | result                                                                     |
| EMPTY          | 0              | 0b_00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000 |
| ALL_BITS_SET   | 0              | 0b_11111111_11111111_11111111_11111111_11111111_11111111_11111111_11111110 |
| HIGHER_BIT_SET | 0              | 0b_10000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000 |
| PATTERN1       | 0              | 0b_01010101_01010101_01010101_01010101_01010101_01010101_01010101_01010100 |
| PATTERN1_INV   | 0              | 0b_10101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010 |
| PATTERN2       | 0              | 0b_11111111_11111111_11111111_11111111_00000000_00000000_00000000_00000000 |
| PATTERN2_INV   | 0              | 0b_00000000_00000000_00000000_00000000_11111111_11111111_11111111_11111110 |
| EMPTY          | 63             | 0b_00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000 |
| ALL_BITS_SET   | 63             | 0b_01111111_11111111_11111111_11111111_11111111_11111111_11111111_11111111 |
| HIGHER_BIT_SET | 63             | 0b_00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000 |
| PATTERN1       | 63             | 0b_01010101_01010101_01010101_01010101_01010101_01010101_01010101_01010101 |
| PATTERN1_INV   | 63             | 0b_00101010_10101010_10101010_10101010_10101010_10101010_10101010_10101010 |
| PATTERN2       | 63             | 0b_01111111_11111111_11111111_11111111_00000000_00000000_00000000_00000000 |
| PATTERN2_INV   | 63             | 0b_00000000_00000000_00000000_00000000_11111111_11111111_11111111_11111111 |
| EMPTY          | 47             | 0b_00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000 |
| ALL_BITS_SET   | 47             | 0b_11111111_11111111_01111111_11111111_11111111_11111111_11111111_11111111 |
| HIGHER_BIT_SET | 47             | 0b_10000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000 |
| PATTERN1       | 47             | 0b_01010101_01010101_01010101_01010101_01010101_01010101_01010101_01010101 |
| PATTERN1_INV   | 47             | 0b_10101010_10101010_00101010_10101010_10101010_10101010_10101010_10101010 |
| PATTERN2       | 47             | 0b_11111111_11111111_01111111_11111111_00000000_00000000_00000000_00000000 |
| PATTERN2_INV   | 47             | 0b_00000000_00000000_00000000_00000000_11111111_11111111_11111111_11111111 |
| EMPTY          | 19             | 0b_00000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000 |
| ALL_BITS_SET   | 19             | 0b_11111111_11111111_11111111_11111111_11111111_11110111_11111111_11111111 |
| HIGHER_BIT_SET | 19             | 0b_10000000_00000000_00000000_00000000_00000000_00000000_00000000_00000000 |
| PATTERN1       | 19             | 0b_01010101_01010101_01010101_01010101_01010101_01010101_01010101_01010101 |
| PATTERN1_INV   | 19             | 0b_10101010_10101010_10101010_10101010_10101010_10100010_10101010_10101010 |
| PATTERN2       | 19             | 0b_11111111_11111111_11111111_11111111_00000000_00000000_00000000_00000000 |
| PATTERN2_INV   | 19             | 0b_00000000_00000000_00000000_00000000_11111111_11110111_11111111_11111111 |

Scenario: clearBit bad parameters

Given data is <initialValue>
And the next step might throw an exception
When data is set to the result of calling clearBit method with parameter value <parameterValue> on it
Then an Exception is thrown

Examples:
| initialValue | parameterValue |
| PATTERN1     | -1             |
| PATTERN2     | -1             |
| PATTERN1     | 64             |
| PATTERN2     | 64             |
| PATTERN1     | 987654         |
| PATTERN2     | 987654         |
| PATTERN1     | -987654        |
| PATTERN2     | -987654        |