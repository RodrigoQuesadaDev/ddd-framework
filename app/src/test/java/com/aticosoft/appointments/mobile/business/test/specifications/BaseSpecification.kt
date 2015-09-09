package com.aticosoft.appointments.mobile.business.test.specifications

import com.aticosoft.appointments.mobile.business.AbstractApplication
import com.aticosoft.appointments.mobile.business.ApplicationComponent
import com.aticosoft.appointments.mobile.business.ApplicationModule
import com.aticosoft.appointments.mobile.business.BuildConfig
import com.aticosoft.appointments.mobile.business.test.common.infrastructure.persistence.TestPersistenceModule
import com.aticosoft.appointments.mobile.business.test.common.model.appointment.AppointmentAwareSpecification
import com.aticosoft.appointments.mobile.business.test.common.model.appointment.AppointmentAwareSpecificationImpl
import dagger.Component
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.TestLifecycleApplication
import org.robolectric.annotation.Config
import java.lang.reflect.Method
import javax.inject.Singleton

/**
 * Created by rodrigo on 10/09/15.
 */
@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(19), application = BaseSpecification.TestApplication::class)
abstract class BaseSpecification : AppointmentAwareSpecification by AppointmentAwareSpecificationImpl() {

    class TestApplication : AbstractApplication<TestApplicationComponent>(), TestLifecycleApplication {

        override protected fun createApplicationComponent(): TestApplicationComponent {
            return DaggerBaseSpecification_TestApplicationComponent.builder()
                    .applicationModule(ApplicationModule(this))
                    .persistenceModule(TestPersistenceModule())
                    .build()
        }

        override fun beforeTest(method: Method) {
        }

        override fun prepareTest(specification: Any) {
            applicationComponent.inject(specification as BaseSpecification)
        }

        override fun afterTest(method: Method) {
        }
    }

    @Singleton
    @Component(modules = arrayOf(ApplicationModule::class))
    interface TestApplicationComponent : ApplicationComponent {

        fun inject(specification: BaseSpecification)
    }
}
