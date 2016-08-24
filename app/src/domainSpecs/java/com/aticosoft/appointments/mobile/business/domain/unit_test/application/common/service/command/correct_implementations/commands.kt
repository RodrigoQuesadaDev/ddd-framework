package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.Lists.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.Maps.Keys.delegateKeys
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.Maps.Values.delegateValues
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.Maps.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.Sets.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.delegate
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
/***************************************************************************************************
 * Simple
 **************************************************************************************************/

internal open class EntityOnPropertyCommand(entity: TestData) : Command() {
    val entity by entity.delegate()
}

internal class EntityOnPropertyOfParentCommand(entity: TestData) : EntityOnPropertyCommand(entity)

/***************************************************************************************************
 * Generics
 **************************************************************************************************/

internal open class EntityOnGenericPropertyCommand<E : TestData>(entity: E) : Command() {
    val entity by entity.delegate()
}

internal class EntityOnGenericPropertyOfParentCommand<E : TestData>(entity: E) : EntityOnGenericPropertyCommand<E>(entity)

/***************************************************************************************************
 * List
 **************************************************************************************************/

internal open class EntityOnListCommand(entities: List<TestData>) : Command() {
    val entities by entities.delegate()
}

internal class EntityOnListOfParentCommand(entities: List<TestData>) : EntityOnListCommand(entities)

/***************************************************************************************************
 * Set
 **************************************************************************************************/

internal open class EntityOnSetCommand(entities: Set<TestData>) : Command() {
    val entities by entities.delegate()
}

internal class EntityOnSetOfParentCommand(entities: Set<TestData>) : EntityOnSetCommand(entities)

/***************************************************************************************************
 * Map as Value
 **************************************************************************************************/

internal open class EntityOnMapAsValueCommand(entityMap: Map<Int, TestData>) : Command() {
    val entityMap by entityMap.delegateValues()
}

internal class EntityOnMapAsValueOfParentCommand(entityMap: Map<Int, TestData>) : EntityOnMapAsValueCommand(entityMap)

/***************************************************************************************************
 * Map as Key
 **************************************************************************************************/

internal open class EntityOnMapAsKeyCommand(entityMap: Map<TestData, Int>) : Command() {
    val entityMap by entityMap.delegateKeys()
}

internal class EntityOnMapAsKeyOfParentCommand(entityMap: Map<TestData, Int>) : EntityOnMapAsKeyCommand(entityMap)

/***************************************************************************************************
 * Map as Value and Key
 **************************************************************************************************/

internal open class EntitiesOnMapAsValueAndKeyCommand(entityMap: Map<TestData, TestData>) : Command() {
    val entityMap by entityMap.delegate()
}

internal class EntitiesOnMapAsValueAndKeyOfParentCommand(entityMap: Map<TestData, TestData>) : EntitiesOnMapAsValueAndKeyCommand(entityMap)