package com.aticosoft.appointments.mobile.business

import dagger.Component
import javax.inject.Singleton

/**
 * Created by rodrigo on 10/09/15.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(applicationConfigurator: AbstractApplication.Configurator)
}
