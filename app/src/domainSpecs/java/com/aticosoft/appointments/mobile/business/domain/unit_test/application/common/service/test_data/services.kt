package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.*
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
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
     * Only use the passed entity but do not modify the database
     **********************************************************************************************/

    class OnlyUseEntity(entity: TestData) : Command() {
        val entity by CommandEntity(entity)
    }

    fun execute(command: OnlyUseEntity) = command.execute {
        entity.toString()
    }

    class OnlyUseEntitiesFromList(entities: List<TestData>) : Command() {
        val entities by CommandEntityList(entities)
    }

    fun execute(command: OnlyUseEntitiesFromList) = command.execute {
        entities.forEach { it.toString() }
    }

    class OnlyUseEntitiesFromSet(entities: Set<TestData>) : Command() {
        val entities by CommandEntitySet(entities)
    }

    fun execute(command: OnlyUseEntitiesFromSet) = command.execute {
        entities.forEach { it.toString() }
    }

    class OnlyUseEntitiesFromMapAsValues(entities: Map<Int, TestData>) : Command() {
        val entityMap by CommandEntityMap(entities)
    }

    fun execute(command: OnlyUseEntitiesFromMapAsValues) = command.execute {
        entityMap.forEach { pair -> pair.value.toString() }
    }

    class OnlyUseEntitiesFromMapAsKeys(entities: Map<TestData, Int>) : Command() {
        val entityMap by CommandEntityKeyMap(entities)
    }

    fun execute(command: OnlyUseEntitiesFromMapAsKeys) = command.execute {
        entityMap.forEach { pair -> pair.key.toString() }
    }

    /***********************************************************************************************
     * Modify the passed entity
     **********************************************************************************************/

    class ModifyEntity(entity: TestData) : Command() {
        val entity by CommandEntity(entity)
    }

    fun execute(command: ModifyEntity) = command.execute {
        entity.value += 10
    }

    class ModifyEntitiesFromList(entities: List<TestData>) : Command() {
        val entities by CommandEntityList(entities)
    }

    fun execute(command: ModifyEntitiesFromList) = command.execute {
        entities.forEach { it.value += 10 }
    }

    class ModifyEntitiesFromSet(entities: Set<TestData>) : Command() {
        val entities by CommandEntitySet(entities)
    }

    fun execute(command: ModifyEntitiesFromSet) = command.execute {
        entities.forEach { it.value += 10 }
    }

    class ModifyEntitiesFromMapAsValues(entities: Map<Int, TestData>) : Command() {
        val entityMap by CommandEntityMap(entities)
    }

    fun execute(command: ModifyEntitiesFromMapAsValues) = command.execute {
        entityMap.forEach { pair -> pair.value.value += 10 }
    }

    class ModifyEntitiesFromMapAsKeys(entities: Map<TestData, Int>) : Command() {
        val entityMap by CommandEntityKeyMap(entities)
    }

    fun execute(command: ModifyEntitiesFromMapAsKeys) = command.execute {
        entityMap.forEach { pair -> pair.key.value += 10 }
    }

    class ModifyAnotherEntity(passedEntity: TestData, val anotherValue: Int) : Command() {
        val passedEntity by CommandEntity(passedEntity)
    }

    fun execute(command: ModifyAnotherEntity) = command.execute {
        passedEntity //just reading the instance so that it is marked as part of the transaction
        c.testDataRepository.find(c.testDataQueries.valueIs(anotherValue))!!.let { data ->
            data.value += 10
        }
    }

    class ConcurrentlyModifyPassedEntity(passedEntity: TestData, val anotherValue: Int) : Command() {
        val passedEntity by CommandEntity(passedEntity)
    }

    fun execute(command: ConcurrentlyModifyPassedEntity) = command.execute {
        val passedEntityValue = passedEntity.value
        thread { execute(ChangeData(passedEntityValue, passedEntityValue + 10)) }.join(TimeUnit.SECONDS.toMillis(5))
        c.testDataRepository.find(c.testDataQueries.valueIs(anotherValue))!!.let { data ->
            data.value += 10
        }
    }
}