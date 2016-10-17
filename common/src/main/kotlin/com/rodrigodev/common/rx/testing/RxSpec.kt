package com.rodrigodev.common.rx.testing

import com.rodrigodev.common.spec.story.SpecStory
import dagger.Component
import rx.schedulers.TestScheduler
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
abstract class RxSpec : SpecStory() {

    @Inject protected lateinit var testScheduler: TestScheduler

    init {
        setUp {
            val component = DaggerRxSpec_TestComponent.create()
            val rxConfigurator = component.rxConfigurator()
            rxConfigurator.configure()
            component.inject(this)
        }
    }

    @Singleton
    @Component(modules = arrayOf(TestRxModule::class))
    interface TestComponent {

        fun inject(spec: RxSpec)
        fun rxConfigurator(): RxTestingConfigurator
    }
}