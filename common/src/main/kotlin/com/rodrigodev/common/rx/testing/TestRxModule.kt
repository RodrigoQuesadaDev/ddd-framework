package com.rodrigodev.common.rx.testing

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 29/10/15.
 */
@Module
class TestRxModule {

    @Provides @Singleton
    fun provideTestScheduler(rxTestingConfigurator: RxTestingConfigurator) = rxTestingConfigurator.registeredSchedulersHook!!.testScheduler
}