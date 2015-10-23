package com.rodrigodev.common.rx.testing

import rx.plugins.RxJavaPlugins
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 29/10/15.
 */
@Singleton
class RxConfigurator @Inject constructor(
        private val rxJavaSchedulersHook: TestRxJavaSchedulersHook
) {
    companion object {
        @Volatile var REGISTERED_SCHEDULERS_HOOK: TestRxJavaSchedulersHook? = null
        //TODO uncomment when KT-8928 is fixed
        /*private set*/
    }

    public var registeredSchedulersHook: TestRxJavaSchedulersHook?
        get() = REGISTERED_SCHEDULERS_HOOK
        private set(value) {
            REGISTERED_SCHEDULERS_HOOK = value
        }

    fun configure() {
        if (registeredSchedulersHook == null) {
            RxJavaPlugins.getInstance().registerSchedulersHook(rxJavaSchedulersHook)
            registeredSchedulersHook = rxJavaSchedulersHook
        }
    }
}