package com.aticosoft.appointments.mobile.business.domain.specs.infrastructure.display.refresh_rate

import com.aticosoft.appointments.mobile.business.domain.specs.common.DomainStory
import com.aticosoft.appointments.mobile.business.domain.specs.infrastructure.display.refresh_rate.DataRefreshRateIsThrottled.TestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import dagger.Component
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 23/10/15.
 */
@Config(application = TestApplicationImpl::class)
internal class DataRefreshRateIsThrottled : DomainStory() {

    @Inject protected lateinit var dataRefreshRateSteps: DataRefreshRateSteps

    override val steps by lazy { arrayOf(dataRefreshRateSteps) }

    @Singleton
    @Component(modules = arrayOf(TestApplicationModule::class))
    interface TestApplicationComponentImpl : TestApplicationComponent<DataRefreshRateIsThrottled>

    class TestApplicationImpl : TestApplication(DaggerDataRefreshRateIsThrottled_TestApplicationComponentImpl::class)
}