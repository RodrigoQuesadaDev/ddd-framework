package com.aticosoft.appointments.mobile.business.domain.infrastructure.rx

import rx.Scheduler

/**
 * Created by rodrigo on 21/10/15.
 */
internal interface RxSchedulers {

    fun immediate(): Scheduler

    fun trampoline(): Scheduler

    fun newThread(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler
}