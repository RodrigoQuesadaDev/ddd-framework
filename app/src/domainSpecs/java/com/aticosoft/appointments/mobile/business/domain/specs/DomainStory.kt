package com.aticosoft.appointments.mobile.business.domain.specs

/**
 * Created by rodrigo on 17/09/15.
 */

import com.aticosoft.appointments.mobile.business.AbstractApplication
import com.aticosoft.appointments.mobile.business.ApplicationComponent
import com.aticosoft.appointments.mobile.business.ApplicationModule
import com.aticosoft.appointments.mobile.business.BuildConfig
import com.aticosoft.appointments.mobile.business.test.common.infrastructure.persistence.TestPersistenceModule
import com.aticosoft.appointments.mobile.business.test.common.model.appointment.AppointmentAwareStory
import com.aticosoft.appointments.mobile.business.test.common.model.appointment.AppointmentAwareStoryImpl
import dagger.Component
import org.jbehave.core.configuration.Configuration
import org.jbehave.core.configuration.MostUsefulConfiguration
import org.jbehave.core.io.LoadFromClasspath
import org.jbehave.core.junit.JUnitStory
import org.jbehave.core.reporters.Format
import org.jbehave.core.reporters.StoryReporterBuilder
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.TestLifecycleApplication
import org.robolectric.annotation.Config
import java.lang.reflect.Method
import javax.inject.Singleton

@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(19), application = DomainStory.TestApplication::class)
abstract public class DomainStory constructor(
        val appointmentAware: AppointmentAwareStoryImpl = AppointmentAwareStoryImpl()
) : JUnitStory(), AppointmentAwareStory by appointmentAware {

    override fun configuration(): Configuration {
        return MostUsefulConfiguration()
                .useStoryLoader(LoadFromClasspath(this.javaClass))
                .useStoryReporterBuilder(
                        StoryReporterBuilder()
                                .withDefaultFormats()
                                .withFormats(Format.CONSOLE, Format.TXT))
    }

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
        }

        override fun afterTest(method: Method) {
        }
    }

    @Singleton
    @Component(modules = arrayOf(ApplicationModule::class))
    interface TestApplicationComponent : ApplicationComponent {

        fun inject(story: DomainStory)
        fun inject(appointmentAware: AppointmentAwareStoryImpl)
    }
}