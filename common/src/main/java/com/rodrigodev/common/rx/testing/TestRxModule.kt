package com.rodrigodev.common.rx.testing

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rodrigo on 29/10/15.
 */
@Module
class TestRxModule {

    @Provides @Singleton
    fun provideTestScheduler(rxConfigurator: RxConfigurator) = rxConfigurator.registeredSchedulersHook!!.testScheduler
}