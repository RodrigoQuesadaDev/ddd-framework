package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
/***************************************************************************************************
 * Simple
 **************************************************************************************************/

internal open class EntityOnPropertyCommand(val entity: TestData) : Command()

internal class EntityOnPropertyOfParentCommand(entity: TestData) : EntityOnPropertyCommand(entity)

/***************************************************************************************************
 * Generics
 **************************************************************************************************/

internal open class EntityOnGenericPropertyCommand<E : TestData>(val entity: E) : Command()

internal class EntityOnGenericPropertyOfParentCommand<E : TestData>(entity: E) : EntityOnGenericPropertyCommand<E>(entity)

/***************************************************************************************************
 * List
 **************************************************************************************************/

internal open class EntityOnListCommand(val entities: List<TestData>) : Command()

internal class EntityOnListOfParentCommand(entities: List<TestData>) : EntityOnListCommand(entities)

/***************************************************************************************************
 * Set
 **************************************************************************************************/

internal open class EntityOnSetCommand(val entities: Set<TestData>) : Command()

internal class EntityOnSetOfParentCommand(entities: Set<TestData>) : EntityOnSetCommand(entities)

/***************************************************************************************************
 * Map as Value
 **************************************************************************************************/

internal open class EntityOnMapAsValueCommand(val entityMap: Map<Int, TestData>) : Command()

internal class EntityOnMapAsValueOfParentCommand(entityMap: Map<Int, TestData>) : EntityOnMapAsValueCommand(entityMap)

/***************************************************************************************************
 * Map as Key
 **************************************************************************************************/

internal open class EntityOnMapAsKeyCommand(val entityMap: Map<TestData, Int>) : Command()

internal class EntityOnMapAsKeyOfParentCommand(entityMap: Map<TestData, Int>) : EntityOnMapAsKeyCommand(entityMap)

/***************************************************************************************************
 * Map as Value and Key
 **************************************************************************************************/

internal open class EntitiesOnMapAsValueAndKeyCommand(val entityMap: Map<TestData, TestData>) : Command()

internal class EntitiesOnMapAsValueAndKeyOfParentCommand(entityMap: Map<TestData, TestData>) : EntitiesOnMapAsValueAndKeyCommand(entityMap)