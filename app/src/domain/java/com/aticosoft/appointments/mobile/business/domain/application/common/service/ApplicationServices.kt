package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Context
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import javax.inject.Inject
import javax.jdo.PersistenceManager
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 23/09/15.
 */
/*internal*/ abstract class ApplicationServices(protected val s: Context) {

    protected inline fun <C : Command> C.execute(call: C.() -> Unit) = s.tm.transactional {
        init(s)
        call()
    }

    abstract class Command {

        private var context: ApplicationServices.Context by notNull()

        internal val persistenceManager: PersistenceManager
            get() = context.persistenceContext.persistenceManager

        /*internal*/ fun init(context: ApplicationServices.Context) {
            this.context = context
        }

        /*internal*/ fun initUsing(parent: Command) {
            init(parent.context)
        }
    }

    class Context @Inject constructor(
            val tm: TransactionManager,
            val persistenceContext: PersistenceContext
    )
}