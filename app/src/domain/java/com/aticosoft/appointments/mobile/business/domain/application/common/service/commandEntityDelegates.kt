@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.DirtyEntityException
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.NonDetachedEntityException
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.NonExistingStaleEntityException
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.StaleEntityException
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import javax.jdo.JDOHelper
import javax.jdo.JDOObjectNotFoundException
import javax.jdo.PersistenceManager
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 04/12/15.
 */
abstract class CommandEntityDelegate<T>(private var value: T) : ReadOnlyProperty<Command, T> {

    private var wasProcessed = false

    override fun getValue(thisRef: Command, property: KProperty<*>): T {
        if (!wasProcessed) {
            value = value.processUsing(thisRef)
            wasProcessed = true
        }
        return value
    }

    protected abstract fun T.processUsing(command: Command): T
}

/***************************************************************************************************
 * Delegates
 **************************************************************************************************/

object CommandEntityDelegates {

    private class CommandEntity<E : Entity>(entity: E) : CommandEntityDelegate<E>(entity) {

        override fun E.processUsing(command: Command) = processEntityUsing(command)
    }

    fun <E : Entity> E.delegate(): CommandEntityDelegate<E> = CommandEntity(this)

    object Lists {
        private class CommandEntityList<E : Entity>(entities: List<E>) : CommandEntityDelegate<List<E>>(entities) {

            override fun List<E>.processUsing(command: Command) = map { it.processEntityUsing(command) }
        }

        fun <E : Entity> List<E>.delegate(): CommandEntityDelegate<List<E>> = CommandEntityList(this)
    }

    object Sets {
        private class CommandEntitySet<E : Entity>(entities: Set<E>) : CommandEntityDelegate<Set<E>>(entities) {

            override fun Set<E>.processUsing(command: Command) = asSequence().map { it.processEntityUsing(command) }.toHashSet()
        }

        fun <E : Entity> Set<E>.delegate(): CommandEntityDelegate<Set<E>> = CommandEntitySet(this)
    }

    object Maps {
        private class CommandEntityMap<E1 : Entity, E2 : Entity>(entityMap: Map<E1, E2>) : CommandEntityDelegate<Map<E1, E2>>(entityMap) {

            override fun Map<E1, E2>.processUsing(command: Command) = asSequence().associateBy({ it.key.processEntityUsing(command) }, { it.value.processEntityUsing(command) })
        }

        fun <E1 : Entity, E2 : Entity> Map<E1, E2>.delegate(): CommandEntityDelegate<Map<E1, E2>> = CommandEntityMap(this)

        object Values {
            private class CommandEntityMapValues<K, E : Entity>(entityMap: Map<K, E>) : CommandEntityDelegate<Map<K, E>>(entityMap) {

                override fun Map<K, E>.processUsing(command: Command) = mapValues { it.value.processEntityUsing(command) }
            }

            fun <K, E : Entity> Map<K, E>.delegateValues(): CommandEntityDelegate<Map<K, E>> = CommandEntityMapValues(this)
        }

        object Keys {
            private class CommandEntityMapKeys<E : Entity, V>(entityMap: Map<E, V>) : CommandEntityDelegate<Map<E, V>>(entityMap) {

                override fun Map<E, V>.processUsing(command: Command) = mapKeys { it.key.processEntityUsing(command) }
            }

            fun <E : Entity, V> Map<E, V>.delegateKeys(): CommandEntityDelegate<Map<E, V>> = CommandEntityMapKeys(this)
        }
    }
}

private inline fun <E : Entity> E.processEntityUsing(command: Command) = command.persistenceManager.process(this)

/***************************************************************************************************
 * Core Logic
 **************************************************************************************************/

private inline fun <E : Entity> PersistenceManager.process(entity: E): E {
    with(entity) {
        checkIsDetached()
        checkIsNotDirty()
    }
    return makeTransactionalCopy(entity)
}

private inline fun Entity.checkIsNotDirty() {
    if (JDOHelper.isDirty(this)) throw DirtyEntityException(this)
}

private inline fun Entity.checkIsDetached() {
    if (!JDOHelper.isDetached(this)) throw NonDetachedEntityException(this)
}

private inline fun Entity.checkIsNotStaleUsing(storedEntity: Entity) {
    if (version != storedEntity.version) throw StaleEntityException(this)
}

private inline fun <E : Entity> PersistenceManager.makeTransactionalCopy(entity: E): E {
    val entityCopy = checkingExists(entity) { getObjectById(entity.javaClass, entity.id) }
    entity.checkIsNotStaleUsing(entityCopy)
    return entityCopy
}

private inline fun <E : Entity, R : Any> checkingExists(entity: E, call: () -> R): R = try {
    call()
}
catch(e: JDOObjectNotFoundException) {
    throw NonExistingStaleEntityException(entity, e)
}

/**************************************************************************************************/