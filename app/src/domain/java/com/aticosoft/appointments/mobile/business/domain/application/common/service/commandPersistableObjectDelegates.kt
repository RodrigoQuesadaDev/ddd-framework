@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.DirtyPersistableObjectException
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.NonDetachedPersistableObjectException
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.NonExistingStalePersistableObjectException
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.StalePersistableObjectException
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.rodrigodev.common.collection.nullCopy
import com.rodrigodev.common.collection.toTypedArray
import javax.jdo.JDOHelper
import javax.jdo.JDOObjectNotFoundException
import javax.jdo.PersistenceManager
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 04/12/15.
 */
abstract class CommandPersistableObjectDelegate<T>(private var value: T) : ReadOnlyProperty<Command, T> {

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

//region Delegates
object CommandPersistableObjectDelegates {

    private class CommandPersistableObject<P : PersistableObject<*>>(obj: P) : CommandPersistableObjectDelegate<P>(obj) {

        override fun P.processUsing(command: Command) = processObjectUsing(command)
    }

    fun <P : PersistableObject<*>> P.delegate(): CommandPersistableObjectDelegate<P> = CommandPersistableObject(this)

    object Arrays {
        private class CommandPersistableObjectArray<P : PersistableObject<*>>(objects: Array<P>) : CommandPersistableObjectDelegate<Array<P>>(objects) {

            override fun Array<P>.processUsing(command: Command) = asSequence().map { it.processObjectUsing(command) }.toTypedArray(nullCopy())
        }

        fun <P : PersistableObject<*>> Array<P>.delegate(): CommandPersistableObjectDelegate<Array<P>> = CommandPersistableObjectArray(this)
    }

    object Lists {
        private class CommandPersistableObjectList<P : PersistableObject<*>>(objects: List<P>) : CommandPersistableObjectDelegate<List<P>>(objects) {

            override fun List<P>.processUsing(command: Command) = map { it.processObjectUsing(command) }
        }

        fun <P : PersistableObject<*>> List<P>.delegate(): CommandPersistableObjectDelegate<List<P>> = CommandPersistableObjectList(this)
    }

    object Sets {
        private class CommandPersistableObjectSet<P : PersistableObject<*>>(objects: Set<P>) : CommandPersistableObjectDelegate<Set<P>>(objects) {

            override fun Set<P>.processUsing(command: Command) = asSequence().map { it.processObjectUsing(command) }.toHashSet()
        }

        fun <P : PersistableObject<*>> Set<P>.delegate(): CommandPersistableObjectDelegate<Set<P>> = CommandPersistableObjectSet(this)
    }

    object Maps {
        private class CommandPersistableObjectMap<P1 : PersistableObject<*>, P2 : PersistableObject<*>>(objectMap: Map<P1, P2>) : CommandPersistableObjectDelegate<Map<P1, P2>>(objectMap) {

            override fun Map<P1, P2>.processUsing(command: Command) = asSequence().associateBy({ it.key.processObjectUsing(command) }, { it.value.processObjectUsing(command) })
        }

        fun <P1 : PersistableObject<*>, P2 : PersistableObject<*>> Map<P1, P2>.delegate(): CommandPersistableObjectDelegate<Map<P1, P2>> = CommandPersistableObjectMap(this)

        object Values {
            private class CommandPersistableObjectMapValues<K, P : PersistableObject<*>>(objectMap: Map<K, P>) : CommandPersistableObjectDelegate<Map<K, P>>(objectMap) {

                override fun Map<K, P>.processUsing(command: Command) = mapValues { it.value.processObjectUsing(command) }
            }

            fun <K, P : PersistableObject<*>> Map<K, P>.delegateValues(): CommandPersistableObjectDelegate<Map<K, P>> = CommandPersistableObjectMapValues(this)
        }

        object Keys {
            private class CommandPersistableObjectMapKeys<P : PersistableObject<*>, V>(objectMap: Map<P, V>) : CommandPersistableObjectDelegate<Map<P, V>>(objectMap) {

                override fun Map<P, V>.processUsing(command: Command) = mapKeys { it.key.processObjectUsing(command) }
            }

            fun <P : PersistableObject<*>, V> Map<P, V>.delegateKeys(): CommandPersistableObjectDelegate<Map<P, V>> = CommandPersistableObjectMapKeys(this)
        }
    }
}

private inline fun <P : PersistableObject<*>> P.processObjectUsing(command: Command) = command.persistenceManager.process(this)
//endregion

//region Core Logic
private inline fun <P : PersistableObject<*>> PersistenceManager.process(obj: P): P {
    with(obj) {
        checkIsDetached()
        checkIsNotDirty()
    }
    return makeTransactionalCopy(obj)
}

private inline fun PersistableObject<*>.checkIsNotDirty() {
    if (JDOHelper.isDirty(this)) throw DirtyPersistableObjectException(this)
}

private inline fun PersistableObject<*>.checkIsDetached() {
    if (!JDOHelper.isDetached(this)) throw NonDetachedPersistableObjectException(this)
}

private inline fun PersistableObject<*>.checkIsNotStaleUsing(storedObject: PersistableObject<*>) {
    if (version != storedObject.version) throw StalePersistableObjectException(this)
}

private inline fun <P : PersistableObject<*>> PersistenceManager.makeTransactionalCopy(obj: P): P {
    val objCopy = checkingExists(obj) { getObjectById(obj.javaClass, obj.id) }
    obj.checkIsNotStaleUsing(objCopy)
    return objCopy
}

private inline fun <P : PersistableObject<*>, R : Any> checkingExists(obj: P, call: () -> R): R = try {
    call()
}
catch(e: JDOObjectNotFoundException) {
    throw NonExistingStalePersistableObjectException(obj, e)
}
//endregion