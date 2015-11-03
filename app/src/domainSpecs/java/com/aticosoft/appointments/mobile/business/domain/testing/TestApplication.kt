package com.aticosoft.appointments.mobile.business.domain.testing

import com.aticosoft.appointments.mobile.business.AbstractApplication
import com.aticosoft.appointments.mobile.business.ApplicationModule
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.test.common.ApplicationComponentConfigurator
import com.rodrigodev.common.rx.testing.RxConfigurator
import org.robolectric.TestLifecycleApplication
import java.lang.reflect.Method
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 23/10/15.
 */
internal abstract class TestApplication(
        private val testApplicationComponent: Class<*>,
        private val testModules: TestApplicationModules = TestApplicationModules()
) : AbstractApplication<TestApplicationComponent<DomainStory>>(), TestLifecycleApplication {

    @Inject protected lateinit var rxConfigurator: RxConfigurator

    override protected fun createApplicationComponent(): TestApplicationComponent<DomainStory> {
        val configurator = ApplicationComponentConfigurator()

        @Suppress("UNCHECKED_CAST")
        val component = configurator.configure(testApplicationComponent, ApplicationModule(this),
                testModules
        ) as TestApplicationComponent<DomainStory>
        component.inject(this)

        rxConfigurator.configure()
        testModules.injectUsing(component)
        return component
    }

    override fun beforeTest(method: Method) {
    }

    override fun prepareTest(test: Any) {
        @Suppress("UNCHECKED_CAST")
        test as DomainStory
        applicationComponent.inject(test)
    }

    override fun afterTest(method: Method) {
    }
}