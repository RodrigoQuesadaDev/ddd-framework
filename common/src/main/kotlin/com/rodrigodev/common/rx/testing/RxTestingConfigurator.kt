package com.rodrigodev.common.rx.testing

import rx.plugins.RxJavaPlugins
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/10/15.
 */
@Singleton
class RxTestingConfigurator @Inject constructor(
        private val rxJavaSchedulersHook: TestRxJavaSchedulersHook
) {
    companion object {
        @Volatile var REGISTERED_SCHEDULERS_HOOK: TestRxJavaSchedulersHook? = null
            private set
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