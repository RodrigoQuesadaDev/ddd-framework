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
class CommandEntity<E : Entity>(private val origEntity: E) : ReadOnlyProperty<Command, E> {

    private var entity: E? = null

    override fun getValue(thisRef: Command, property: KProperty<*>): E = entity ?: with(this) {
        entity = thisRef.persistenceManager().process(origEntity)
        entity!!
    }
}

class CommandEntityList<E : Entity>(private val origEntities: Collection<E>) : ReadOnlyProperty<Command, List<E>> {

    private var entityList: List<E>? = null

    override fun getValue(thisRef: Command, property: KProperty<*>): List<E> = entityList ?: with(this) {
        entityList = origEntities.map { thisRef.persistenceManager().process(it) }
        entityList!!
    }
}

class CommandEntitySet<E : Entity>(private val origEntities: Collection<E>) : ReadOnlyProperty<Command, Set<E>> {

    private var entitySet: Set<E>? = null

    override fun getValue(thisRef: Command, property: KProperty<*>): Set<E> = entitySet ?: with(this) {
        entitySet = origEntities.asSequence().map { thisRef.persistenceManager().process(it) }.toHashSet()
        entitySet!!
    }
}

class CommandEntityMap<K, E : Entity>(private val origEntities: Map<K, E>) : ReadOnlyProperty<Command, Map<K, E>> {

    private var entityMap: Map<K, E>? = null

    override fun getValue(thisRef: Command, property: KProperty<*>): Map<K, E> = entityMap ?: with(this) {
        entityMap = origEntities.mapValues { thisRef.persistenceManager().process(it.value) }
        entityMap!!
    }
}

class CommandEntityKeyMap<E : Entity, V>(private val origEntities: Map<E, V>) : ReadOnlyProperty<Command, Map<E, V>> {

    private var entityMap: Map<E, V>? = null

    override fun getValue(thisRef: Command, property: KProperty<*>): Map<E, V> = entityMap ?: with(this) {
        entityMap = origEntities.mapKeys { thisRef.persistenceManager().process(it.key) }
        entityMap!!
    }
}

/***************************************************************************************************
 * Core Logic
 **************************************************************************************************/

private inline fun Command.persistenceManager() = context.persistenceContext.persistenceManager

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