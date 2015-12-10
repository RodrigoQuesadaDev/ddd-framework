package com.aticosoft.appointments.mobile.business

import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.EntityInjection
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/09/15.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent : EntityInjection {

    fun inject(mainActivity: MainActivity)
    fun inject(applicationConfigurator: Application.Configurator)
}
