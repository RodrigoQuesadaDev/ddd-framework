package com.aticosoft.appointments.mobile.business.domain.application

import com.aticosoft.appointments.mobile.business.domain.application.ApplicationService.Services
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.TransactionManager
import javax.inject.Inject

/**
 * Created by rodrigo on 23/09/15.
 */
internal abstract class ApplicationService<C : ApplicationCommand>(
        private val s: Services
) {

    fun execute(command: C) = s.transactionManager.transactional { doExecute(command) }

    protected abstract fun doExecute(command: C)

    protected class Services @Inject constructor(
            val transactionManager: TransactionManager
    )
}

internal interface ApplicationCommand