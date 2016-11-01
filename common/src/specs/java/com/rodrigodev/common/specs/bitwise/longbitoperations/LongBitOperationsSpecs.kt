@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.specs.bitwise.longbitoperations

import com.rodrigodev.common.bitwise.LongBitOperations.clear
import com.rodrigodev.common.bitwise.LongBitOperations.clearBit
import com.rodrigodev.common.bitwise.LongBitOperations.isEmpty
import com.rodrigodev.common.bitwise.LongBitOperations.isFull
import com.rodrigodev.common.bitwise.LongBitOperations.setAllBits
import com.rodrigodev.common.bitwise.LongBitOperations.setBit
import com.rodrigodev.common.spec.story.SpecStory
import com.rodrigodev.common.spec.story.steps.ExceptionThrowingSteps
import com.rodrigodev.common.string.parseUnsignedBinaryLong
import com.rodrigodev.common.string.parseUnsignedHexLong
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When

/**
 * Created by Rodrigo Quesada on 31/10/16.
 */
internal class LongBitOperationsSpecs : SpecStory() {

    init {
        steps { listOf(LocalSteps()) }
    }

    class LocalSteps : ExceptionThrowingSteps {

        override var _thrownException: Throwable? = null
        override var _catchException: Boolean = false

        private var data: Long = 0
        private var booleanResult: Boolean = false

        @Given("data is \$initialValue")
        fun givenDataIs(initialValue: BitSetConstant) {
            data = initialValue.value
        }

        @When("data is set to the result of calling \$methodName method on it")
        fun whenDataIsSetToTheResultOfCallingMethodOnIt(methodName: String) {
            data = when (methodName) {
                "clear" -> data.clear()
                "setAllBits" -> data.setAllBits()
                else -> throw IllegalArgumentException()
            }
        }

        @When("data is set to the result of calling \$methodName method with parameter value \$parameterValue on it")
        fun whenDataIsSetToTheResultOfCallingMethodWithParameterOnIt(methodName: String, parameterValue: Int) = mightThrowException {
            data = when (methodName) {
                "setBit" -> data.setBit(parameterValue)
                "clearBit" -> data.clearBit(parameterValue)
                else -> throw IllegalArgumentException()
            }
        }

        @When("boolean method \$methodName is called on data")
        fun whenBooleanMethodIsCalledOnData(methodName: String) {
            booleanResult = when (methodName) {
                "isEmpty" -> data.isEmpty()
                "isFull" -> data.isFull()
                else -> throw IllegalArgumentException()
            }
        }

        @Then("data is equal to value \$value")
        fun thenDataIsEqualToValue(value: Long) {
            assertThat(data).isEqualTo(value)
        }

        @Then("data is equal to constant \$constant")
        fun thenDataIsEqualToConstant(constant: BitSetConstant) {
            assertThat(data).isEqualTo(constant.value)
        }

        @Then("the boolean result is \$result")
        fun thenTheBooleanResultIs(result: Boolean) {
            assertThat(booleanResult).isEqualTo(result)
        }

        @Then("an Exception is thrown")
        fun thenAnExceptionIsThrown() {
            assertThat(thrownException).isNotNull()
        }

        //region Sanity Checks
        @Given("constant \$constant is equal to value \${value}")
        fun givenConstantIsEqualToValue(constant: BitSetConstant, value: Long) {
            assertThat(constant.value).isEqualTo(value)
        }

        @Given("constant \$constant is equal to inverting value \$value")
        fun givenConstantIsEqualToInvertingValue(constant: BitSetConstant, value: Long) {
            assertThat(constant.value).isEqualTo(value.inv())
        }

        @Given("constant \$constant is equal to \$value shifted to the left \$times times")
        fun givenConstantIsEqualToValueShiftedToTheLeftXTimes(constant: BitSetConstant, value: Long, times: Int) {
            assertThat(constant.value).isEqualTo(value.shl(times))
        }
        //endregion

        enum class BitSetConstant(val value: Long) {
            EMPTY(0),
            HIGHER_BIT_SET("0x8000_0000_0000_0000".parseUnsignedHexLong()),
            ALL_BITS_SET("0xFFFF_FFFF_FFFF_FFFF".parseUnsignedHexLong()),
            // Java's long data type is a 64-bit two's complement integer.
            PATTERN1("0b_01010101_01010101_01010101_01010101_01010101_01010101_01010101_01010101".parseUnsignedBinaryLong()),
            PATTERN1_INV(PATTERN1.value.inv()),
            PATTERN2("0b_11111111_11111111_11111111_11111111_00000000_00000000_00000000_00000000".parseUnsignedBinaryLong()),
            PATTERN2_INV(PATTERN2.value.inv())
        }
    }
}