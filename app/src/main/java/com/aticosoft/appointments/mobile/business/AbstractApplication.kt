package com.aticosoft.appointments.mobile.business

import android.app.Application

/**
 * Created by rodrigo on 10/09/15.
 */
/*internal*/ abstract class AbstractApplication<C : ApplicationComponent> : Application() {

    lateinit var applicationComponent: C
        private set

    override fun onCreate() {
        super.onCreate()
        applicationComponent = createApplicationComponent()
    }

    protected abstract fun createApplicationComponent(): C
}