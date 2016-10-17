package com.aticosoft.appointments.mobile.business.domain.testing

import com.aticosoft.appointments.mobile.business.Application
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.rodrigodev.common.rx.testing.RxTestingConfigurator
import org.robolectric.TestLifecycleApplication
import java.lang.reflect.Method
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 23/10/15.
 */
internal abstract class TestApplication<S : DomainStory, C : TestApplicationComponent, B : TestApplicationComponent.Builder<C, B>>(
        private val createBuilder: () -> B,
        private val injectTest: C.(S) -> Unit
) : Application<C, B>(), TestLifecycleApplication {

    private val c: Context = Context()

    override val testingMode = true

    @Suppress("UNCHECKED_CAST")
    override protected fun createApplicationComponentBuilder(): B = createBuilder()

    override fun C.afterBuild() {
        component.inject(c)
        c.rxTestingConfigurator.configure()
    }

    class Context() {
        @Inject lateinit var rxTestingConfigurator: RxTestingConfigurator
    }

    //region Test Lifecycle
    override fun beforeTest(method: Method) {
    }

    @Suppress("UNCHECKED_CAST")
    override fun prepareTest(test: Any) {
        test as S
        component.injectTest(test)
    }

    override fun afterTest(method: Method) {
    }
    //endregion
}