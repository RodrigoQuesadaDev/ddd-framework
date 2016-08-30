@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObjectLifecycleListener
import com.aticosoft.appointments.mobile.business.domain.model.configuration.services.ConfigurationManager
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.querydsl.core.types.Path
import com.rodrigodev.common.reflection.isSuperOfOrSameAs
import org.datanucleus.api.jdo.NucleusJDOHelper
import javax.inject.Inject
import javax.jdo.JDOHelper
import javax.jdo.listener.InstanceLifecycleEvent
import javax.jdo.listener.StoreLifecycleListener

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
/*internal*/ abstract class PersistableObjectValidator<P : PersistableObject<*>, X : PersistableObjectValidationException>(
        private val createException: (String) -> X, private vararg val validatedFields: Path<*>
) : PersistableObjectLifecycleListener<P>, StoreLifecycleListener {

    private lateinit var m: InjectedMembers<P>

    override final val objectType: Class<P>
        get() = m.objectType

    private fun init() {
        validatedFields.check()
    }

    abstract fun P.errorMessage(): String

    abstract fun P.isValid(): Boolean

    private inline fun validate(obj: P) {
        if (!obj.isValid()) throw createException(obj.errorMessage())
    }

    protected fun retrieveConfiguration() = m.configurationManager.retrieve()

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<P>) {
        m = injectedMembers
        init()
    }

    protected class InjectedMembers<P : PersistableObject<*>> @Inject protected constructor(
            val objectType: Class<P>,
            val persistenceContext: PersistenceContext,
            val configurationManager: ConfigurationManager
    )
    //endregion

    //region Lifecycle Methods
    override fun preStore(event: InstanceLifecycleEvent) {
        @Suppress("UNCHECKED_CAST")
        (event.source as P).let { obj ->
            if (JDOHelper.isNew(obj) || obj.anyValidatedFieldIsDirty()) validate(obj)
        }
    }

    override fun postStore(event: InstanceLifecycleEvent) {
        // Do nothing!
    }
    //endregion

    //region Definition Checks
    private inline fun Array<out Path<*>>.check() = forEach {
        it.metadata.parent.let { parent ->
            check(parent != null, { "Incorrectly specified fields for PersistableObjectValidator." })
            check(parent!!.metadata.isRoot, { "Specified fields for PersistableObjectValidator must be direct fields of root element." })
            check(parent.type.isSuperOfOrSameAs(m.objectType), { "Specified fields for PersistableObjectValidator must belong to the object being validated." })
        }
    }
    //endregion

    //region Fields
    private inline fun P.anyValidatedFieldIsDirty() = validatedFields.any { it.isDirty(this) }

    private inline fun Path<*>.isDirty(obj: P) = NucleusJDOHelper.isDirty(obj, metadata.name, m.persistenceContext.persistenceManager)
    //endregion
}