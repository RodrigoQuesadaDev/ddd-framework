package com.aticosoft.appointments.mobile.business

import android.app.Application
import javax.inject.Inject

/**
 * Created by rodrigo on 10/09/15.
 */
/*internal*/ abstract class AbstractApplication<C : ApplicationComponent> : Application() {

    lateinit var applicationComponent: C
        protected set

    override fun onCreate() {
        super.onCreate()
        applicationComponent = createApplicationComponent()
        applicationComponent.inject(Configurator)
        Configurator.configure()
    }

    protected abstract fun createApplicationComponent(): C

    object Configurator {
        @Inject protected lateinit var modulePostInitializer: ApplicationModule.PostInitializer

        fun configure() {
            modulePostInitializer.init()
        }
    }
}