package com.rodrigodev.common.rx.testing

import rx.plugins.RxJavaSchedulersHook
import rx.schedulers.TestScheduler
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 21/10/15.
 */
@Singleton
class TestRxJavaSchedulersHook @Inject constructor() : RxJavaSchedulersHook() {

    val testScheduler = TestScheduler()

    override fun getIOScheduler() = testScheduler

    override fun getComputationScheduler() = testScheduler

    override fun getNewThreadScheduler() = testScheduler
}