@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Context
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.DirtyEntityException
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.NonDetachedEntityException
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.NonExistingStaleEntityException
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.StaleEntityException
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import javax.inject.Inject
import javax.jdo.JDOHelper
import javax.jdo.JDOObjectNotFoundException
import javax.jdo.PersistenceManager
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by Rodrigo Quesada on 23/09/15.
 */
/*internal*/ abstract class ApplicationServices(protected val s: Context) {

    protected inline fun <C : Command> C.execute(call: C.() -> Unit) = s.tm.transactional {
        context = s
        call()
    }

    abstract class Command() {

        /*internal*/ lateinit var context: Context

        class EntityDelegate<E : Entity>(private val origEntity: E) : ReadOnlyProperty<Command, E> {

            private var entity: E? = null

            override fun getValue(thisRef: Command, property: KProperty<*>): E = entity?.apply {} ?: with(thisRef.context.persistenceContext.persistenceManager) {
                with(origEntity) {
                    checkIsDetached()
                    checkIsNotDirty()
                }
                entity = makeTransactionalCopy(origEntity)
                entity as E
            }
        }
    }

    class Context @Inject constructor(
            val tm: TransactionManager,
            val persistenceContext: PersistenceContext
    )
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
    val entityCopy = try {
        getObjectById(entity.javaClass, entity.id)
    }
    catch(e: JDOObjectNotFoundException) {
        throw NonExistingStaleEntityException(entity, e)
    }
    entity.checkIsNotStaleUsing(entityCopy)
    return entityCopy
}