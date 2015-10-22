package com.aticosoft.appointments.mobile.business.infrastructure.rx

import com.aticosoft.appointments.mobile.business.domain.infrastructure.rx.RxSchedulers
import rx.schedulers.Schedulers
import javax.inject.Singleton

/**
 * Created by rodrigo on 21/10/15.
 */
@Singleton
/*internal*/ open class RxSchedulersImpl : RxSchedulers {

    override fun immediate() = Schedulers.immediate()

    override fun trampoline() = Schedulers.trampoline()

    override fun newThread() = Schedulers.newThread()

    override fun computation() = Schedulers.computation()

    override fun io() = Schedulers.io()
}