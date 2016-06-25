package com.aticosoft.appointments.mobile.business.domain.testing

import com.aticosoft.appointments.mobile.business.ApplicationComponent

/**
 * Created by Rodrigo Quesada on 23/10/15.
 */
internal interface TestApplicationComponent : ApplicationComponent {

    fun inject(applicationContext: TestApplication.Context)

    interface Builder<C : TestApplicationComponent, B : TestApplicationComponent.Builder<C, B>> : ApplicationComponent.Builder<C, B>
}