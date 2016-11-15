package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.correct_implementations

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.Arrays.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.Iterables.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.Maps.Keys.delegateKeys
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.Maps.Values.delegateValues
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.Maps.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.Arrays.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.Lists.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.Maps.Keys.delegateKeys
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.Maps.Values.delegateValues
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.Maps.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.Sets.delegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegates.delegate
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.common.NestedCommand

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
//region Simple
internal class NestedCommandOnPropertyCommand(command: NestedCommand) : Command() {
    val command by command.delegate()
}

internal open class EntityOnPropertyCommand(entity: TestData) : Command() {
    val entity by entity.delegate()
}

internal class EntityOnPropertyOfParentCommand(entity: TestData) : EntityOnPropertyCommand(entity)
//endregion

//region Generics
internal class NestedCommandOnGenericPropertyCommand<N : NestedCommand>(command: N) : Command() {
    val command by command.delegate()
}

internal open class EntityOnGenericPropertyCommand<E : TestData>(entity: E) : Command() {
    val entity by entity.delegate()
}

internal class EntityOnGenericPropertyOfParentCommand<E : TestData>(entity: E) : EntityOnGenericPropertyCommand<E>(entity)
//endregion

//region Array
internal class NestedCommandOnArrayCommand(commands: Array<NestedCommand>) : Command() {
    val commands by commands.delegate()
}

internal open class EntityOnArrayCommand(entities: Array<TestData>) : Command() {
    val entities by entities.delegate()
}

internal class EntityOnArrayOfParentCommand(entities: Array<TestData>) : EntityOnArrayCommand(entities)
//endregion

//region List
internal class NestedCommandOnListCommand(commands: List<NestedCommand>) : Command() {
    val commands by commands.delegate()
}

internal open class EntityOnListCommand(entities: List<TestData>) : Command() {
    val entities by entities.delegate()
}

internal class EntityOnListOfParentCommand(entities: List<TestData>) : EntityOnListCommand(entities)
//endregion

//region Set
internal class NestedCommandOnSetCommand(commands: Set<NestedCommand>) : Command() {
    val commands by commands.delegate()
}

internal open class EntityOnSetCommand(entities: Set<TestData>) : Command() {
    val entities by entities.delegate()
}

internal class EntityOnSetOfParentCommand(entities: Set<TestData>) : EntityOnSetCommand(entities)
//endregion

//region Map as Value
internal class NestedCommandOnMapAsValueCommand(commandMap: Map<Int, NestedCommand>) : Command() {
    val commandMap by commandMap.delegateValues()
}

internal open class EntityOnMapAsValueCommand(entityMap: Map<Int, TestData>) : Command() {
    val entityMap by entityMap.delegateValues()
}

internal class EntityOnMapAsValueOfParentCommand(entityMap: Map<Int, TestData>) : EntityOnMapAsValueCommand(entityMap)
//endregion

//region Map as Key
internal class NestedCommandOnMapAsKeyCommand(commandMap: Map<NestedCommand, Int>) : Command() {
    val commandMap by commandMap.delegateKeys()
}

internal open class EntityOnMapAsKeyCommand(entityMap: Map<TestData, Int>) : Command() {
    val entityMap by entityMap.delegateKeys()
}

internal class EntityOnMapAsKeyOfParentCommand(entityMap: Map<TestData, Int>) : EntityOnMapAsKeyCommand(entityMap)
//endregion

//region Map as Value and Key
internal class NestedCommandsOnMapAsValueAndKeyCommand(commandMap: Map<NestedCommand, NestedCommand>) : Command() {
    val commandMap by commandMap.delegate()
}

internal open class EntitiesOnMapAsValueAndKeyCommand(entityMap: Map<TestData, TestData>) : Command() {
    val entityMap by entityMap.delegate()
}

internal class EntitiesOnMapAsValueAndKeyOfParentCommand(entityMap: Map<TestData, TestData>) : EntitiesOnMapAsValueAndKeyCommand(entityMap)
//endregion