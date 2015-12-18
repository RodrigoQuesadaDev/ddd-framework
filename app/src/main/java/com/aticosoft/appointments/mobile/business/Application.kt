package com.aticosoft.appointments.mobile.business

import android.app.Application
import com.aticosoft.appointments.mobile.business.domain.application.ModelServices
import com.aticosoft.appointments.mobile.business.domain.application.ModelServices.InitializeModel
import javax.inject.Inject
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 10/09/15.
 */
/*internal*/ abstract class Application<C : ApplicationComponent> : Application() {

    private val c: Context = Context()

    var component: C by notNull()
        private set

    override fun onCreate() {
        super.onCreate()
        component = createApplicationComponent()

        Configurator().let { configurator ->
            component.inject(configurator)
            configurator.configure()
        }
        component.inject(this.c)

        c.modelServices.execute(InitializeModel())
    }

    protected abstract fun createApplicationComponent(): C

    class Configurator {

        @Inject protected lateinit var modulePostInitializer: ApplicationModule.PostInitializer

        fun configure() {
            modulePostInitializer.init()
        }
    }

    class Context {
        @Inject lateinit var modelServices: ModelServices
    }
}