package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities

import com.rodrigodev.common.spec.story.converter.ParameterConverterBase
import java.lang.reflect.Type
import java.util.*

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
private val typeMap: MutableMap<String, UsageType> = HashMap()

internal enum class UsageType(text: String) {
    LIST("a list"),
    SET("a set"),
    VALUES_OF_MAP("a map with them as values"),
    KEYS_OF_MAP("a map with them as keys");

    init {
        typeMap[text] = this
    }

    companion object {
        fun from(text: String): UsageType = typeMap[text] ?: fail("Unrecognized usage: \"$text\"")
    }
}

internal class UsageTypeConverter : ParameterConverterBase<UsageType>(UsageType::class.java) {

    override fun convertValue(value: String, type: Type) = UsageType.from(value)
}

//TODO remove thwne kotlin.test.fail is fixed (that is when it returns Nothing instead of Unit)
private fun fail(message: String? = null): Nothing = kotlin.test.fail(message) as Nothing