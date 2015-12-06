package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities

import com.rodrigodev.common.spec.story.converter.ParameterConverterBase
import java.lang.reflect.Type

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
internal enum class SimpleUsageType(override val text: String) : UsageType {
    SIMPLE_FIELD("a simple field"),
    NESTED_FIELD("a nested field");

    companion object : UsageType.CompanionDefinition<SimpleUsageType>(values())
}

internal class SimpleUsageTypeConverter : UsageTypeConverter<SimpleUsageType>(SimpleUsageType::class.java) {

    override fun convertValue(value: String, type: Type) = convertValue(value, SimpleUsageType.Companion)
}

internal enum class CollectionUsageType(override val text: String) : UsageType {
    LIST("a list"),
    NESTED_LIST("a nested list"),
    LIST_OF_NESTED_ENTITIES("a list of nested entities"),
    SET("a set"),
    NESTED_SET("a nested set"),
    SET_OF_NESTED_ENTITIES("a set of nested entities"),
    VALUES_OF_MAP("a map with them as values"),
    VALUES_OF_NESTED_MAP("a nested map with them as values"),
    MAP_OF_NESTED_ENTITIES_AS_VALUES("a map with nested entities as values"),
    KEYS_OF_MAP("a map with them as keys"),
    KEYS_OF_NESTED_MAP("a nested map with them as keys"),
    MAP_OF_NESTED_ENTITIES_AS_KEYS("a map with nested entities as keys"),
    VALUES_AND_KEYS_OF_MAP("a map with them as values and keys"),
    VALUES_AND_KEYS_OF_NESTED_MAP("a nested map with them as values and keys"),
    MAP_OF_NESTED_ENTITIES_AS_VALUES_AND_KEYS("a map with nested entities as values and keys");

    companion object : UsageType.CompanionDefinition<CollectionUsageType>(values())
}

internal class CollectionUsageTypeConverter : UsageTypeConverter<CollectionUsageType>(CollectionUsageType::class.java) {

    override fun convertValue(value: String, type: Type) = convertValue(value, CollectionUsageType.Companion)
}

/***************************************************************************************************
 * Base Code
 **************************************************************************************************/

internal abstract class UsageTypeConverter<U : UsageType>(usageTypeClass: Class<U>) : ParameterConverterBase<U>(usageTypeClass) {

    fun convertValue(value: String, usageTypeCompanion: UsageType.CompanionDefinition<U>) = usageTypeCompanion.from(value)
}

internal interface UsageType {
    val text: String

    open class CompanionDefinition<out U : UsageType>(values: Array<U>) {
        private val typeMap: Map<String, U> = values.toMapBy { it.text }

        fun from(text: String): U = typeMap[text] ?: error("Unrecognized usage: \"$text\"")
    }
}