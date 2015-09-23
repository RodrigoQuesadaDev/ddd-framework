package com.aticosoft.appointments.mobile.business

/**
 * Created by rodrigo on 10/09/15.
 */
internal class Application : AbstractApplication<ApplicationComponent>() {

    override protected fun createApplicationComponent() = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
}
