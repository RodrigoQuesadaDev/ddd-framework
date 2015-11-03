package com.rodrigodev.common.testing.number

/**
 * Created by Rodrigo Quesada on 11/11/15.
 */
fun Int.isOdd() = this and 1 == 1

fun Int.isEven() = this and 1 == 0

fun Int.isPrime(): Boolean = (2..this - 1).none { this.isDivisibleBy(it) }

fun primeNumbersUpTo(max: Int): List<Int> = (2..max).filter(Int::isPrime)

fun Int.isDivisibleBy(divisor: Int) = this % divisor == 0