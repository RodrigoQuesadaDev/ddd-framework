@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.Iterables.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.Maps.Keys.delegateKeys
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.Maps.Values.delegateValues
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.Maps.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandEntityDelegates.Lists.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandEntityDelegates.Maps.Keys.delegateKeys
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandEntityDelegates.Maps.Values.delegateValues
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandEntityDelegates.Maps.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandEntityDelegates.Sets.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandEntityDelegates.delegate
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data.LocalTestDataServices.NestedValue
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
@Singleton
internal class LocalTestDataServices @Inject constructor(private val c: TestDataServices.Context) : TestDataServices(c) {

    /***********************************************************************************************
     * Only Use Entity
     **********************************************************************************************/

    class OnlyUseEntity(entity: TestData) : Command() {
        val entity by entity.delegate()
    }

    fun execute(command: OnlyUseEntity) = command.execute { entity.use() }

    class OnlyUseEntityFromNestedProperty(entity: TestData) : Command() {
        val nested by NestedValue(entity).delegate()
    }

    fun execute(command: OnlyUseEntityFromNestedProperty) = command.execute { nested.entity.use() }

    class OnlyUseEntitiesFromList(entities: List<TestData>) : Command() {
        val entities by entities.delegate()
    }

    fun execute(command: OnlyUseEntitiesFromList) = command.execute { entities.use() }

    class OnlyUseEntitiesFromNestedList(entities: List<TestData>) : Command() {
        val nested by NestedList(entities).delegate()
    }

    fun execute(command: OnlyUseEntitiesFromNestedList) = command.execute { nested.entities.use() }

    class OnlyUseNestedEntitiesFromList(entities: List<TestData>) : Command() {
        val nestedEntities by entities.toNestedEntities().delegate()
    }

    fun execute(command: OnlyUseNestedEntitiesFromList) = command.execute { nestedEntities.useNested() }

    class OnlyUseEntitiesFromSet(entities: Set<TestData>) : Command() {
        val entities by entities.delegate()
    }

    fun execute(command: OnlyUseEntitiesFromSet) = command.execute { entities.use() }

    class OnlyUseEntitiesFromNestedSet(entities: Set<TestData>) : Command() {
        val nested by NestedSet(entities).delegate()
    }

    fun execute(command: OnlyUseEntitiesFromNestedSet) = command.execute { nested.entities.use() }

    class OnlyUseNestedEntitiesFromSet(entities: Set<TestData>) : Command() {
        val nestedEntities by entities.toNestedEntities().delegate()
    }

    fun execute(command: OnlyUseNestedEntitiesFromSet) = command.execute { nestedEntities.useNested() }

    class OnlyUseEntitiesFromMapAsValues(entityMap: Map<Int, TestData>) : Command() {
        val entityMap by entityMap.delegateValues()
    }

    fun execute(command: OnlyUseEntitiesFromMapAsValues) = command.execute { entityMap.values.use() }

    class OnlyUseEntitiesFromNestedMapAsValues(entityMap: Map<Int, TestData>) : Command() {
        val nested by NestedMapAsValues(entityMap).delegate()
    }

    fun execute(command: OnlyUseEntitiesFromNestedMapAsValues) = command.execute { nested.entityMap.values.use() }

    class OnlyUseNestedEntitiesFromMapAsValues(entityMap: Map<Int, TestData>) : Command() {
        val nestedEntityMap by entityMap.mapValuesToNestedEntities().delegateValues()
    }

    fun execute(command: OnlyUseNestedEntitiesFromMapAsValues) = command.execute { nestedEntityMap.values.useNested() }

    class OnlyUseEntitiesFromMapAsKeys(entityMap: Map<TestData, Int>) : Command() {
        val entityMap by entityMap.delegateKeys()
    }

    fun execute(command: OnlyUseEntitiesFromMapAsKeys) = command.execute { entityMap.keys.use() }

    class OnlyUseEntitiesFromNestedMapAsKeys(entityMap: Map<TestData, Int>) : Command() {
        val nested by NestedMapAsKeys(entityMap).delegate()
    }

    fun execute(command: OnlyUseEntitiesFromNestedMapAsKeys) = command.execute { nested.entityMap.keys.use() }

    class OnlyUseNestedEntitiesFromMapAsKeys(entityMap: Map<TestData, Int>) : Command() {
        val nestedEntityMap by entityMap.mapKeysToNestedEntities().delegateKeys()
    }

    fun execute(command: OnlyUseNestedEntitiesFromMapAsKeys) = command.execute { nestedEntityMap.keys.useNested() }

    class OnlyUseEntitiesFromMapAsValuesAndKeys(entityMap: Map<TestData, TestData>) : Command() {
        val entityMap by entityMap.delegate()
    }

    fun execute(command: OnlyUseEntitiesFromMapAsValuesAndKeys) = command.execute { entityMap.use() }

    class OnlyUseEntitiesFromNestedMapAsValuesAndKeys(entityMap: Map<TestData, TestData>) : Command() {
        val nested by NestedMapAsValuesAndKeys(entityMap).delegate()
    }

    fun execute(command: OnlyUseEntitiesFromNestedMapAsValuesAndKeys) = command.execute { nested.entityMap.use() }

    class OnlyUseNestedEntitiesFromMapAsValuesAndKeys(entityMap: Map<TestData, TestData>) : Command() {
        val nestedEntityMap by entityMap.toNestedEntities().delegate()
    }

    fun execute(command: OnlyUseNestedEntitiesFromMapAsValuesAndKeys) = command.execute { nestedEntityMap.useNested() }

    /***********************************************************************************************
     * Modify Entity
     **********************************************************************************************/

    class ModifyEntity(entity: TestData) : Command() {
        val entity by entity.delegate()
    }

    fun execute(command: ModifyEntity) = command.execute { entity.modify() }

    class ModifyEntityFromNestedProperty(entity: TestData) : Command() {
        val nested by NestedValue(entity).delegate()
    }

    fun execute(command: ModifyEntityFromNestedProperty) = command.execute { nested.modify() }

    class ModifyEntitiesFromList(entities: List<TestData>) : Command() {
        val entities by entities.delegate()
    }

    fun execute(command: ModifyEntitiesFromList) = command.execute { entities.modify() }

    class ModifyEntitiesFromNestedList(entities: List<TestData>) : Command() {
        val nested by NestedList(entities).delegate()
    }

    fun execute(command: ModifyEntitiesFromNestedList) = command.execute { nested.entities.modify() }

    class ModifyEntitiesFromListOfNestedEntities(entities: List<TestData>) : Command() {
        val nestedEntities by entities.toNestedEntities().delegate()
    }

    fun execute(command: ModifyEntitiesFromListOfNestedEntities) = command.execute { nestedEntities.modifyNested() }

    class ModifyEntitiesFromSet(entities: Set<TestData>) : Command() {
        val entities by entities.delegate()
    }

    fun execute(command: ModifyEntitiesFromSet) = command.execute { entities.modify() }

    class ModifyEntitiesFromNestedSet(entities: Set<TestData>) : Command() {
        val nested by NestedSet(entities).delegate()
    }

    fun execute(command: ModifyEntitiesFromNestedSet) = command.execute { nested.entities.modify() }

    class ModifyEntitiesFromSetOfNestedEntities(entities: Set<TestData>) : Command() {
        val nestedEntities by entities.toNestedEntities().delegate()
    }

    fun execute(command: ModifyEntitiesFromSetOfNestedEntities) = command.execute { nestedEntities.modifyNested() }

    class ModifyEntitiesFromMapAsValues(entityMap: Map<Int, TestData>) : Command() {
        val entityMap by entityMap.delegateValues()
    }

    fun execute(command: ModifyEntitiesFromMapAsValues) = command.execute { entityMap.values.modify() }

    class ModifyEntitiesFromNestedMapAsValues(entityMap: Map<Int, TestData>) : Command() {
        val nested by NestedMapAsValues(entityMap).delegate()
    }

    fun execute(command: ModifyEntitiesFromNestedMapAsValues) = command.execute { nested.entityMap.values.modify() }

    class ModifyNestedEntitiesFromMapAsValues(entityMap: Map<Int, TestData>) : Command() {
        val nestedEntityMap by entityMap.mapValuesToNestedEntities().delegateValues()
    }

    fun execute(command: ModifyNestedEntitiesFromMapAsValues) = command.execute { nestedEntityMap.values.modifyNested() }

    class ModifyEntitiesFromMapAsKeys(entityMap: Map<TestData, Int>) : Command() {
        val entityMap by entityMap.delegateKeys()
    }

    fun execute(command: ModifyEntitiesFromMapAsKeys) = command.execute { entityMap.keys.modify() }

    class ModifyEntitiesFromNestedMapAsKeys(entityMap: Map<TestData, Int>) : Command() {
        val nested by NestedMapAsKeys(entityMap).delegate()
    }

    fun execute(command: ModifyEntitiesFromNestedMapAsKeys) = command.execute { nested.entityMap.keys.modify() }

    class ModifyNestedEntitiesFromMapAsKeys(entityMap: Map<TestData, Int>) : Command() {
        val nestedEntityMap by entityMap.mapKeysToNestedEntities().delegateKeys()
    }

    fun execute(command: ModifyNestedEntitiesFromMapAsKeys) = command.execute { nestedEntityMap.keys.modifyNested() }

    class ModifyEntitiesFromMapAsValuesAndKeys(entityMap: Map<TestData, TestData>) : Command() {
        val entityMap by entityMap.delegate()
    }

    fun execute(command: ModifyEntitiesFromMapAsValuesAndKeys) = command.execute { entityMap.modify() }

    class ModifyEntitiesFromNestedMapAsValuesAndKeys(entityMap: Map<TestData, TestData>) : Command() {
        val nested by NestedMapAsValuesAndKeys(entityMap).delegate()
    }

    fun execute(command: ModifyEntitiesFromNestedMapAsValuesAndKeys) = command.execute { nested.entityMap.modify() }

    class ModifyNestedEntitiesFromMapAsValuesAndKeys(entityMap: Map<TestData, TestData>) : Command() {
        val nestedEntityMap by entityMap.toNestedEntities().delegate()
    }

    fun execute(command: ModifyNestedEntitiesFromMapAsValuesAndKeys) = command.execute { nestedEntityMap.modifyNested() }

    /***********************************************************************************************
     * Modify Entity Differently
     **********************************************************************************************/

    class ModifyAnotherEntity(passedEntity: TestData, val anotherValue: Int) : Command() {
        val passedEntity by passedEntity.delegate()
    }

    fun execute(command: ModifyAnotherEntity) = command.execute {
        passedEntity //just reading the instance so that it is marked as part of the transaction
        c.testDataRepository.find(c.testDataQueries.valueIs(anotherValue))!!.let { data ->
            data.modify()
        }
    }

    class ConcurrentlyModifyPassedEntity(passedEntity: TestData, val anotherValue: Int) : Command() {
        val passedEntity by passedEntity.delegate()
    }

    fun execute(command: ConcurrentlyModifyPassedEntity) = command.execute {
        val passedEntityValue = passedEntity.value
        thread { execute(ChangeData(passedEntityValue, passedEntityValue + 10)) }.join(TimeUnit.SECONDS.toMillis(5))
        c.testDataRepository.find(c.testDataQueries.valueIs(anotherValue))!!.let { data ->
            data.modify()
        }
    }

    /***********************************************************************************************
     * Nested Classes
     **********************************************************************************************/

    class NestedValue(entity: TestData) : Command() {
        val entity by entity.delegate()
    }

    class NestedList(entities: List<TestData>) : Command() {
        val entities by entities.delegate()
    }

    class NestedSet(entities: Set<TestData>) : Command() {
        val entities by entities.delegate()
    }

    class NestedMapAsValues(entityMap: Map<Int, TestData>) : Command() {
        val entityMap by entityMap.delegateValues()
    }

    class NestedMapAsKeys(entityMap: Map<TestData, Int>) : Command() {
        val entityMap by entityMap.delegateKeys()
    }

    class NestedMapAsValuesAndKeys(entityMap: Map<TestData, TestData>) : Command() {
        val entityMap by entityMap.delegate()
    }
}

/***************************************************************************************************
 * Only Use Entity
 **************************************************************************************************/

private inline fun TestData.use() = toString()

private inline fun NestedValue.use() = run { entity.use() }

private inline fun Iterable<TestData>.use() = forEach { it.use() }

private inline fun Iterable<NestedValue>.useNested() = forEach { it.use() }

private inline fun Map<TestData, TestData>.use() = forEach {
    it.key.use()
    it.value.use()
}

private inline fun Map<NestedValue, NestedValue>.useNested() = forEach {
    it.key.use()
    it.value.use()
}

/***************************************************************************************************
 * Modify Entity
 **************************************************************************************************/

private inline fun TestData.modify() = run { value += 10 }

private inline fun NestedValue.modify() = run { entity.modify() }

private inline fun Iterable<TestData>.modify() = forEach { it.modify() }

private inline fun Iterable<NestedValue>.modifyNested() = forEach { it.modify() }

private inline fun Map<TestData, TestData>.modify() = forEach {
    it.key.modify()
    it.value.modify()
}

private inline fun Map<NestedValue, NestedValue>.modifyNested() = forEach {
    it.key.modify()
    it.value.modify()
}

/***************************************************************************************************
 * Map to Nested Entity
 **************************************************************************************************/

private inline fun List<TestData>.toNestedEntities() = map { NestedValue(it) }

private inline fun Set<TestData>.toNestedEntities() = map { NestedValue(it) }.toSet()

private inline fun Map<Int, TestData>.mapValuesToNestedEntities(): Map<Int, NestedValue> = mapValues { NestedValue(it.value) }

private inline fun Map<TestData, Int>.mapKeysToNestedEntities(): Map<NestedValue, Int> = mapKeys { NestedValue(it.key) }

private inline fun Map<TestData, TestData>.toNestedEntities(): Map<NestedValue, NestedValue> = asSequence().toMap({ NestedValue(it.key) }, { NestedValue(it.value) })