package com.aticosoft.appointments.mobile.business

import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/09/15.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {

    fun inject(application: Application.Context)
    //TODO this is not necessary anymore because you can inject MembersInjectors
    fun inject(mainActivity: MainActivity)

    //TODO this is part of what needs to be changed for removing PostInitializers
    fun inject(applicationConfigurator: Application.Configurator)

    interface Builder<C : ApplicationComponent, B : Builder<C, B>> {
        fun build(): C
        fun applicationBaseModule(applicationBaseModule: ApplicationBaseModule): B
    }

    @Component.Builder
    interface BuilderImpl : Builder<ApplicationComponent, BuilderImpl>
}
