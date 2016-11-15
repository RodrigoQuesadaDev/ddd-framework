@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.exceptions.ReusedCommandException
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.jdo.PersistenceManager

/**
 * Created by Rodrigo Quesada on 23/09/15.
 */
/*internal*/ abstract class ApplicationServices protected constructor() {

    private lateinit var m: InjectedMembers

    protected fun <C : Command> C.execute(call: C.() -> Unit) {
        checkIfReused()
        init(m)
        m.persistenceContext.execute { call() }
    }

    private inline fun <C : Command> C.checkIfReused() {
        if (wasUsed) throw ReusedCommandException()
    }

    abstract class Command {

        private var m: ApplicationServices.InjectedMembers? = null

        internal val wasUsed: Boolean
            get() = m != null

        internal val persistenceManager: PersistenceManager
            get() = m!!.persistenceContext.persistenceManager

        internal fun init(injectedMembers: InjectedMembers) {
            this.m = injectedMembers
        }

        internal fun initUsing(parent: Command) {
            m = parent.m
        }
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers) {
        m = injectedMembers
    }

    class InjectedMembers @Inject protected constructor(
            val persistenceContext: PersistenceContext
    )
    //endregion
}