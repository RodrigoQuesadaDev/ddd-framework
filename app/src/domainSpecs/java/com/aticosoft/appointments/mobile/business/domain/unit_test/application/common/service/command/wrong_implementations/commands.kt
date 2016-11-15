package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.wrong_implementations

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.common.NestedCommand

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
//region Simple
internal class NestedCommandOnPropertyCommand(val command: NestedCommand) : Command()

internal open class EntityOnPropertyCommand(val entity: TestData) : Command()

internal class EntityOnPropertyOfParentCommand(entity: TestData) : EntityOnPropertyCommand(entity)
//endregion

//region Generics
internal class NestedCommandOnGenericPropertyCommand<out N : NestedCommand>(val command: N) : Command()

internal open class EntityOnGenericPropertyCommand<out E : TestData>(val entity: E) : Command()

internal class EntityOnGenericPropertyOfParentCommand<out E : TestData>(entity: E) : EntityOnGenericPropertyCommand<E>(entity)
//endregion

//region Array
internal class NestedCommandOnArrayCommand(val commands: Array<NestedCommand>) : Command()

internal open class EntityOnArrayCommand(val entities: Array<TestData>) : Command()

internal class EntityOnArrayOfParentCommand(entities: Array<TestData>) : EntityOnArrayCommand(entities)
//endregion

//region List
internal class NestedCommandOnListCommand(val commands: List<NestedCommand>) : Command()

internal open class EntityOnListCommand(val entities: List<TestData>) : Command()

internal class EntityOnListOfParentCommand(entities: List<TestData>) : EntityOnListCommand(entities)
//endregion

//region Set
internal class NestedCommandOnSetCommand(val commands: Set<NestedCommand>) : Command()

internal open class EntityOnSetCommand(val entities: Set<TestData>) : Command()

internal class EntityOnSetOfParentCommand(entities: Set<TestData>) : EntityOnSetCommand(entities)
//endregion

//region Map as Value
internal class NestedCommandOnMapAsValueCommand(val commandMap: Map<Int, NestedCommand>) : Command()

internal open class EntityOnMapAsValueCommand(val entityMap: Map<Int, TestData>) : Command()

internal class EntityOnMapAsValueOfParentCommand(entityMap: Map<Int, TestData>) : EntityOnMapAsValueCommand(entityMap)
//endregion

//region Map as Key
internal open class NestedCommandOnMapAsKeyCommand(val commandMap: Map<NestedCommand, Int>) : Command()

internal open class EntityOnMapAsKeyCommand(val entityMap: Map<TestData, Int>) : Command()

internal class EntityOnMapAsKeyOfParentCommand(entityMap: Map<TestData, Int>) : EntityOnMapAsKeyCommand(entityMap)
//endregion

//region Map as Value and Key
internal class NestedCommandsOnMapAsValueAndKeyCommand(val commandMap: Map<NestedCommand, NestedCommand>) : Command()

internal open class EntitiesOnMapAsValueAndKeyCommand(val entityMap: Map<TestData, TestData>) : Command()

internal class EntitiesOnMapAsValueAndKeyOfParentCommand(entityMap: Map<TestData, TestData>) : EntitiesOnMapAsValueAndKeyCommand(entityMap)
//endregion