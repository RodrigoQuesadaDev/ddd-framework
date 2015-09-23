package com.aticosoft.appointments.mobile.business.domain.specs.stories

/**
 * Created by rodrigo on 17/09/15.
 */

import com.aticosoft.appointments.mobile.business.AbstractApplication
import com.aticosoft.appointments.mobile.business.ApplicationComponent
import com.aticosoft.appointments.mobile.business.ApplicationModule
import com.aticosoft.appointments.mobile.business.BuildConfig
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.appointment.AppointmentServicesAware
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.appointment.AppointmentServicesAwareImpl
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.client.ClientServicesAware
import com.aticosoft.appointments.mobile.business.domain.specs.common.model.client.ClientServicesAwareImpl
import com.aticosoft.appointments.mobile.business.domain.specs.test.common.infrastructure.persistence.TestPersistenceModule
import dagger.Component
import org.jbehave.core.configuration.Configuration
import org.jbehave.core.configuration.MostUsefulConfiguration
import org.jbehave.core.failures.FailingUponPendingStep
import org.jbehave.core.io.LoadFromClasspath
import org.jbehave.core.junit.JUnitStory
import org.jbehave.core.reporters.Format
import org.jbehave.core.reporters.StoryReporterBuilder
import org.jbehave.core.steps.ParameterControls
import org.jbehave.core.steps.ParameterConverters
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.TestLifecycleApplication
import org.robolectric.annotation.Config
import java.lang.reflect.Method
import javax.inject.Singleton

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(19), application = DomainStory.TestApplication::class)
internal abstract class DomainStory constructor(
        private val appointmentAware: AppointmentServicesAwareImpl = AppointmentServicesAwareImpl(),
        private val clientAware: ClientServicesAwareImpl = ClientServicesAwareImpl()
) : JUnitStory(), AppointmentServicesAware by appointmentAware, ClientServicesAware by clientAware {

    override fun configuration(): Configuration {
        return MostUsefulConfiguration()
                .useParameterConverters(ParameterConverters().addConverters(*customConverters()))
                .useParameterControls(ParameterControls()
                        .useDelimiterNamedParameters(true)
                )
                .usePendingStepStrategy(FailingUponPendingStep())
                .useStoryLoader(LoadFromClasspath(this.javaClass))
                .useStoryReporterBuilder(
                        StoryReporterBuilder()
                                .withDefaultFormats()
                                .withFormats(Format.CONSOLE, Format.HTML_TEMPLATE)
                                .withFailureTrace(true)
                                .withRelativeDirectory("../build/jbehave"))
    }

    protected abstract fun customConverters(): Array<ParameterConverters.ParameterConverter>

    class TestApplication : AbstractApplication<TestApplicationComponent>(), TestLifecycleApplication {

        override protected fun createApplicationComponent(): TestApplicationComponent {
            return DaggerDomainStory_TestApplicationComponent.builder()
                    .applicationModule(ApplicationModule(this))
                    .persistenceModule(TestPersistenceModule())
                    .build()
        }

        override fun beforeTest(method: Method) {
        }

        override fun prepareTest(story: Any) {
            story as DomainStory
            applicationComponent.inject(story)
            applicationComponent.inject(story.appointmentAware)
            applicationComponent.inject(story.clientAware)
        }

        override fun afterTest(method: Method) {
        }
    }

    @Singleton
    @Component(modules = arrayOf(ApplicationModule::class))
    interface TestApplicationComponent : ApplicationComponent {

        fun inject(story: DomainStory)
        fun inject(appointmentAware: AppointmentServicesAwareImpl)
        fun inject(clientAware: ClientServicesAwareImpl)
    }
}