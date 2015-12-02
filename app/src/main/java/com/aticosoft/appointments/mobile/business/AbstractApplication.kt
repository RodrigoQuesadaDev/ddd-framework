package com.aticosoft.appointments.mobile.business

import android.app.Application
import javax.inject.Inject
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 10/09/15.
 */
/*internal*/ abstract class AbstractApplication<C : ApplicationComponent> : Application() {

    var applicationComponent: C by notNull()
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent = createApplicationComponent()
        Configurator().let { configurator ->
            applicationComponent.inject(configurator)
            configurator.configure()
        }
    }

    protected abstract fun createApplicationComponent(): C

    class Configurator {

        @Inject protected lateinit var modulePostInitializer: ApplicationModule.PostInitializer

        fun configure() {
            modulePostInitializer.init()
        }
    }
}