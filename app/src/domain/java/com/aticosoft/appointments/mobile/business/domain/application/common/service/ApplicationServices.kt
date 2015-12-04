package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Context
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 23/09/15.
 */
/*internal*/ abstract class ApplicationServices(protected val s: Context) {

    protected inline fun <C : Command> C.execute(call: C.() -> Unit) = s.tm.transactional {
        context = s
        call()
    }

    abstract class Command() {
        /*internal*/ lateinit var context: ApplicationServices.Context
    }

    class Context @Inject constructor(
            val tm: TransactionManager,
            val persistenceContext: PersistenceContext
    )
}