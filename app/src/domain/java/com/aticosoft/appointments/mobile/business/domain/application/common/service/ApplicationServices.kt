@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.ReusedCommandException
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import javax.inject.Inject
import javax.jdo.PersistenceManager

/**
 * Created by Rodrigo Quesada on 23/09/15.
 */
/*internal*/ abstract class ApplicationServices(private val c: Context) {

    protected fun <C : Command> C.execute(call: C.() -> Unit) {
        checkIfReused()
        init(c)
        c.persistenceContext.execute { call() }
    }

    private inline fun <C : Command> C.checkIfReused() {
        if (wasUsed) throw ReusedCommandException()
    }

    abstract class Command {

        private var context: ApplicationServices.Context? = null

        internal val wasUsed: Boolean
            get() = context != null

        internal val persistenceManager: PersistenceManager
            get() = context!!.persistenceContext.persistenceManager

        internal fun init(context: Context) {
            this.context = context
        }

        internal fun initUsing(parent: Command) {
            context = parent.context
        }
    }

    //TODO use InjectedMembers pattern
    class Context @Inject protected constructor(
            val tm: TransactionManager,
            val persistenceContext: PersistenceContext
    )
}