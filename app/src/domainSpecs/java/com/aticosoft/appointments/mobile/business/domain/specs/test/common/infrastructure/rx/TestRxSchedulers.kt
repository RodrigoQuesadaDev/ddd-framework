package com.aticosoft.appointments.mobile.business.domain.specs.test.common.infrastructure.rx

import com.aticosoft.appointments.mobile.business.infrastructure.rx.RxSchedulersImpl
import rx.schedulers.Schedulers

/**
 * Created by rodrigo on 21/10/15.
 */
internal class TestRxSchedulers : RxSchedulersImpl() {

    override fun immediate() = Schedulers.immediate()

    override fun trampoline() = Schedulers.immediate()

    override fun newThread() = Schedulers.immediate()

    override fun computation() = Schedulers.immediate()

    override fun io() = Schedulers.immediate()
}