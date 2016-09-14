package com.aticosoft.appointments.mobile.business.domain.specs

import com.aticosoft.appointments.mobile.business.BuildConfig
import com.rodrigodev.common.spec.story.SpecStory
import com.rodrigodev.common.spec.story.steps.SpecSteps
import org.jbehave.core.annotations.Given
import org.junit.runner.RunWith
import org.robolectric.RobolectricGradleTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/09/15.
 */
@RunWith(RobolectricGradleTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(19))
internal abstract class DomainStory : SpecStory() {

    @Inject protected lateinit var domainGlobalSteps: DomainGlobalSteps

    init {
        steps { listOf(domainGlobalSteps) }
    }
}

@Singleton
internal class DomainGlobalSteps @Inject protected constructor() : SpecSteps() {

    @Given("dummy step")
    fun givenDummyStep() {
    }
}