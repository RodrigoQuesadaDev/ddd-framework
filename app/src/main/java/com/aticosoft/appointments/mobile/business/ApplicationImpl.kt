package com.aticosoft.appointments.mobile.business

/**
* Created by Rodrigo Quesada on 10/09/15.
*/
/*internal*/ class ApplicationImpl : Application<ApplicationComponent>() {

    override protected fun createApplicationComponent() = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
}
