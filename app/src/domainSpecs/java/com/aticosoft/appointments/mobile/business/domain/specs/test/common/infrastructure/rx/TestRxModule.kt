package com.aticosoft.appointments.mobile.business.domain.specs.test.common.infrastructure.rx

import com.aticosoft.appointments.mobile.business.infrastructure.rx.RxModule

/**
 * Created by rodrigo on 21/10/15.
 */
internal class TestRxModule : RxModule() {

    override fun provideRxSchedulers() = TestRxSchedulers()
}