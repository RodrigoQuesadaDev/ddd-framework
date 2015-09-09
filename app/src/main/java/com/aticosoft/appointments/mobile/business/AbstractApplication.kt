package com.aticosoft.appointments.mobile.business

import android.app.Application
import kotlin.properties.Delegates

/**
 * Created by rodrigo on 10/09/15.
 */
abstract class AbstractApplication<C : ApplicationComponent> : Application() {

    var applicationComponent: C by Delegates.notNull()
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent = createApplicationComponent()
    }

    protected abstract fun createApplicationComponent(): C
}