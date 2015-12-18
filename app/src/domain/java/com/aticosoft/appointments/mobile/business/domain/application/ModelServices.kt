package com.aticosoft.appointments.mobile.business.domain.application

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.ModelInitializerService
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@Singleton
/*internal*/ class ModelServices @Inject protected constructor(
        context: ApplicationServices.Context,
        private val modelInitializerService: ModelInitializerService
) : ApplicationServices(context) {

    class InitializeModel() : Command()

    fun execute(command: InitializeModel) = command.execute { modelInitializerService.run() }
}