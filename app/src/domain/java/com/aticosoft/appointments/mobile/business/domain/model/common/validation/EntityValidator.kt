@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.validation

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.EntityLifecycleListener
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.querydsl.core.types.Path
import org.datanucleus.api.jdo.NucleusJDOHelper
import javax.inject.Inject
import javax.jdo.JDOHelper
import javax.jdo.listener.InstanceLifecycleEvent
import javax.jdo.listener.StoreLifecycleListener

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
/*internal*/ abstract class EntityValidator<E : Entity>(private val validatedFields: Array<out Path<*>>) : EntityLifecycleListener<E>, StoreLifecycleListener {


    lateinit private var c: Context

    fun initialize(context: Context) {
        c = context
        validatedFields.check()
    }

    abstract fun E.errorMessage(): String

    abstract fun E.isValid(): Boolean

    private inline fun validate(entity: E) {
        if (!entity.isValid()) throw ValidationException(createErrorMessage(entity))
    }

    fun createErrorMessage(entity: E) = entity.errorMessage()

    override fun preStore(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        (event.source as E).let { entity ->
            if (JDOHelper.isNew(entity) || entity.validatedFieldIsDirty()) validate(entity)
        }
    }

    override fun postStore(event: InstanceLifecycleEvent) {
        // Do nothing!
    }

    class Context protected @Inject constructor(
            var persistenceContext: PersistenceContext
    )

    /***********************************************************************************************
     * Definition Checks
     **********************************************************************************************/

    private inline fun Array<out Path<*>>.check() = forEach {
        it.metadata.parent.let { parent ->
            check(parent != null, { "Incorrectly specified fields for EntityValidator." })
            check(parent!!.metadata.isRoot, { "Specified fields for EntityValidator must be direct fields of root element." })
            check(parent.type.isAssignableFrom(entityType), { "Specified fields for EntityValidator must belong to the entity being validated." })
        }
    }

    /***********************************************************************************************
     * Fields
     **********************************************************************************************/

    private inline fun E.validatedFieldIsDirty() = validatedFields.any { it.isDirty(this) }

    private inline fun Path<*>.isDirty(entity: E) = NucleusJDOHelper.isDirty(entity, metadata.name, c.persistenceContext.persistenceManager)
}