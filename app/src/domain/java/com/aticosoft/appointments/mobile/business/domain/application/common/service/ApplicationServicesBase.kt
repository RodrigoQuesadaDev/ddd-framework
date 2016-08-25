package com.aticosoft.appointments.mobile.business.domain.application.common.service

import com.aticosoft.appointments.mobile.business.domain.model.configuration.services.ConfigurationManager
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 21/12/15.
 */
/*internal*/ abstract class ApplicationServicesBase protected constructor() : ApplicationServices() {

    private lateinit var m: InjectedMembers

    protected fun retrieveConfiguration() = m.configurationManager.retrieve()

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers) {
        m = injectedMembers
    }

    protected class InjectedMembers @Inject protected constructor(
            val configurationManager: ConfigurationManager
    )
    //endregion
}