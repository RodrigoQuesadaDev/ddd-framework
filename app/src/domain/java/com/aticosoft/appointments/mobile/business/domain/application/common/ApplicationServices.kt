package com.aticosoft.appointments.mobile.business.domain.application.common

import com.aticosoft.appointments.mobile.business.domain.application.common.ApplicationServices.Context
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 23/09/15.
 */
/*internal*/ abstract class ApplicationServices(protected val s: Context) {

    protected inline fun <C : Command> C.execute(call: C.() -> Unit) = s.transactionManager.transactional { this.call() }

    abstract class Command

    class Context @Inject constructor(
            val transactionManager: TransactionManager
    )
}