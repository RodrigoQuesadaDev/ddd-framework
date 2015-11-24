package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Context
import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.DirtyEntityException
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import javax.inject.Inject
import javax.jdo.JDOHelper

/**
 * Created by Rodrigo Quesada on 23/09/15.
 */
/*internal*/ abstract class ApplicationServices(protected val s: Context) {

    protected inline fun <C : Command> C.execute(call: C.() -> Unit) = s.tm.transactional { this.call() }

    abstract class Command(private vararg val entities: Entity) {
        init {
            entities.forEach {
                if (JDOHelper.isDirty(it)) throw DirtyEntityException("Entity is dirty: $it")
            }
        }
    }

    class Context @Inject constructor(
            val tm: TransactionManager
    )
}