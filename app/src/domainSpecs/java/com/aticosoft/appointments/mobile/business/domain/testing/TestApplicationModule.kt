package com.aticosoft.appointments.mobile.business.domain.testing

import com.aticosoft.appointments.mobile.business.ApplicationModule
import com.rodrigodev.common.rx.testing.TestRxModule
import dagger.Module

/**
* Created by Rodrigo Quesada on 29/10/15.
*/
@Module(includes = arrayOf(ApplicationModule::class, TestRxModule::class))
internal class TestApplicationModule