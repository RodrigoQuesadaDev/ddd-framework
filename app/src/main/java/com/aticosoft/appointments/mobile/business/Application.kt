package com.aticosoft.appointments.mobile.business

import android.app.Application
import com.aticosoft.appointments.mobile.business.domain.application.ModelServices
import com.aticosoft.appointments.mobile.business.domain.application.ModelServices.InitializeModel
import com.aticosoft.appointments.mobile.business.infrastructure.rx.RxConfigurator
import javax.inject.Inject
import kotlin.properties.Delegates.notNull

/**
 * Created by Rodrigo Quesada on 10/09/15.
 */
/*internal*/ abstract class Application<C : ApplicationComponent, B : ApplicationComponent.Builder<C, B>> : Application() {

    private val c: Context = Context()

    var component: C by notNull()
        private set

    open val testingMode: Boolean = false

    override fun onCreate() {
        super.onCreate()
        component = createApplicationComponentBuilder()
                .applicationBaseModule(ApplicationBaseModule(this))
                .build()

        component.afterBuild()

        Configurator().let { configurator ->
            component.inject(configurator)
            configurator.configure()
        }
        component.inject(this.c)

        c.modelServices.execute(InitializeModel())
    }

    protected abstract fun createApplicationComponentBuilder(): B

    protected open fun C.afterBuild() {
    }

    class Configurator {

        @Inject protected lateinit var rxConfigurator: RxConfigurator
        @Inject protected lateinit var modulePostInitializer: ApplicationModule.PostInitializer

        fun configure() {
            rxConfigurator.configure()
            modulePostInitializer.init()
        }
    }

    class Context {
        @Inject lateinit var modelServices: ModelServices
    }
}

/*internal*/ class ApplicationImpl : com.aticosoft.appointments.mobile.business.Application<ApplicationComponent, ApplicationComponent.BuilderImpl>() {

    override fun createApplicationComponentBuilder() = DaggerApplicationComponent.builder()
}