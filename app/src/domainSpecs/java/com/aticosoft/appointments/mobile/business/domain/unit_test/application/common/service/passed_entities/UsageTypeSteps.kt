@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities

import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.CollectionUsageType.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.SimpleUsageType.NESTED_FIELD
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.SimpleUsageType.SIMPLE_FIELD
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data.CommandTestDataServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data.CommandTestDataServices.*

/**
 * Created by Rodrigo Quesada on 07/12/15.
 */
internal interface UsageTypeSteps {

    val testDataServices: CommandTestDataServices

    fun TestData.modify(usageType: SimpleUsageType) = let { entity ->
        with(testDataServices) {
            when (usageType) {
                SIMPLE_FIELD -> execute(ModifyEntity(entity))
                NESTED_FIELD -> execute(ModifyEntityFromNestedProperty(entity))
            }
        }
    }

    fun TestData.onlyUse(usageType: SimpleUsageType) = let { entity ->
        with(testDataServices) {
            when (usageType) {
                SIMPLE_FIELD -> execute(OnlyUseEntity(entity))
                NESTED_FIELD -> execute(OnlyUseEntityFromNestedProperty(entity))
            }
        }
    }

    fun List<TestData>.modify(usageType: CollectionUsageType) = let { entities ->
        with(testDataServices) {
            when (usageType) {
                ARRAY -> execute(ModifyEntitiesFromArray(entities.toTypedArray()))
                NESTED_ARRAY -> execute(ModifyEntitiesFromNestedArray(entities.toTypedArray()))
                ARRAY_OF_NESTED_ENTITIES -> execute(ModifyEntitiesFromArrayOfNestedEntities(entities.toTypedArray()))
                LIST -> execute(ModifyEntitiesFromList(entities))
                NESTED_LIST -> execute(ModifyEntitiesFromNestedList(entities))
                LIST_OF_NESTED_ENTITIES -> execute(ModifyEntitiesFromListOfNestedEntities(entities))
                SET -> execute(ModifyEntitiesFromSet(entities.toSet()))
                NESTED_SET -> execute(ModifyEntitiesFromNestedSet(entities.toSet()))
                SET_OF_NESTED_ENTITIES -> execute(ModifyEntitiesFromSetOfNestedEntities(entities.toSet()))
                VALUES_OF_MAP -> execute(ModifyEntitiesFromMapAsValues(entities.toMapAsValues()))
                VALUES_OF_NESTED_MAP -> execute(ModifyEntitiesFromNestedMapAsValues(entities.toMapAsValues()))
                MAP_OF_NESTED_ENTITIES_AS_VALUES -> execute(ModifyNestedEntitiesFromMapAsValues(entities.toMapAsValues()))
                KEYS_OF_MAP -> execute(ModifyEntitiesFromMapAsKeys(entities.toMapAsKeys()))
                KEYS_OF_NESTED_MAP -> execute(ModifyEntitiesFromNestedMapAsKeys(entities.toMapAsKeys()))
                MAP_OF_NESTED_ENTITIES_AS_KEYS -> execute(ModifyNestedEntitiesFromMapAsKeys(entities.toMapAsKeys()))
                VALUES_AND_KEYS_OF_MAP -> execute(ModifyEntitiesFromMapAsValuesAndKeys(entities.toMapAsValuesAndKeys()))
                VALUES_AND_KEYS_OF_NESTED_MAP -> execute(ModifyEntitiesFromNestedMapAsValuesAndKeys(entities.toMapAsValuesAndKeys()))
                MAP_OF_NESTED_ENTITIES_AS_VALUES_AND_KEYS -> execute(ModifyNestedEntitiesFromMapAsValuesAndKeys(entities.toMapAsValuesAndKeys()))
            }
        }
    }

    fun List<TestData>.onlyUseEntities(usageType: CollectionUsageType) = let { entities ->
        with(testDataServices) {
            when (usageType) {
                ARRAY -> execute(OnlyUseEntitiesFromArray(entities.toTypedArray()))
                NESTED_ARRAY -> execute(OnlyUseEntitiesFromNestedArray(entities.toTypedArray()))
                ARRAY_OF_NESTED_ENTITIES -> execute(OnlyUseNestedEntitiesFromArray(entities.toTypedArray()))
                LIST -> execute(OnlyUseEntitiesFromList(entities))
                NESTED_LIST -> execute(OnlyUseEntitiesFromNestedList(entities))
                LIST_OF_NESTED_ENTITIES -> execute(OnlyUseNestedEntitiesFromList(entities))
                SET -> execute(OnlyUseEntitiesFromSet(entities.toSet()))
                NESTED_SET -> execute(OnlyUseEntitiesFromNestedSet(entities.toSet()))
                SET_OF_NESTED_ENTITIES -> execute(OnlyUseNestedEntitiesFromSet(entities.toSet()))
                VALUES_OF_MAP -> execute(OnlyUseEntitiesFromMapAsValues(entities.toMapAsValues()))
                VALUES_OF_NESTED_MAP -> execute(OnlyUseEntitiesFromNestedMapAsValues(entities.toMapAsValues()))
                MAP_OF_NESTED_ENTITIES_AS_VALUES -> execute(OnlyUseNestedEntitiesFromMapAsValues(entities.toMapAsValues()))
                KEYS_OF_MAP -> execute(OnlyUseEntitiesFromMapAsKeys(entities.toMapAsKeys()))
                KEYS_OF_NESTED_MAP -> execute(OnlyUseEntitiesFromNestedMapAsKeys(entities.toMapAsKeys()))
                MAP_OF_NESTED_ENTITIES_AS_KEYS -> execute(OnlyUseNestedEntitiesFromMapAsKeys(entities.toMapAsKeys()))
                VALUES_AND_KEYS_OF_MAP -> execute(OnlyUseEntitiesFromMapAsValuesAndKeys(entities.toMapAsValuesAndKeys()))
                VALUES_AND_KEYS_OF_NESTED_MAP -> execute(OnlyUseEntitiesFromNestedMapAsValuesAndKeys(entities.toMapAsValuesAndKeys()))
                MAP_OF_NESTED_ENTITIES_AS_VALUES_AND_KEYS -> execute(OnlyUseNestedEntitiesFromMapAsValuesAndKeys(entities.toMapAsValuesAndKeys()))
            }
        }
    }
}

private inline fun List<TestData>.toMapAsValues() = associateBy { it.value }

private inline fun List<TestData>.toMapAsKeys() = associateBy({ it }, { it.value })

private inline fun List<TestData>.toMapAsValuesAndKeys() = associateBy({ it }, { it })